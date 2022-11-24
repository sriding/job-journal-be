package com.jobjournal.JobJournal.controllers.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobjournal.JobJournal.controllers.rest.ABSTRACT_MUST_EXTEND.RequiredAbstractClassForControllers;
import com.jobjournal.JobJournal.exceptions.handlers.CompanyNotFoundException;
import com.jobjournal.JobJournal.exceptions.handlers.PostNotFoundException;
import com.jobjournal.JobJournal.exceptions.handlers.UserIdDoesNotMatchException;
import com.jobjournal.JobJournal.exceptions.handlers.UserIdNotFoundException;
import com.jobjournal.JobJournal.repositories.CompanyRepository;
import com.jobjournal.JobJournal.repositories.PostRepository;
import com.jobjournal.JobJournal.repositories.UsersRepository;
import com.jobjournal.JobJournal.services.CompanyServices;
import com.jobjournal.JobJournal.services.PostServices;
import com.jobjournal.JobJournal.services.UsersServices;
import com.jobjournal.JobJournal.shared.models.entity.Company;
import com.jobjournal.JobJournal.shared.models.entity.Post;

@RestController
@RequestMapping(path = "/api/company")
@CrossOrigin
public class CompanyController extends RequiredAbstractClassForControllers {
    // Services
    private final CompanyServices companyServices;
    private final PostServices postsServices;
    private final UsersServices usersServices;

    // Add in repositories for services to use
    @Autowired
    public CompanyController(CompanyRepository companyRepository, PostRepository postRepository,
            UsersRepository usersRepository) {
        this.companyServices = new CompanyServices(companyRepository);
        this.postsServices = new PostServices(postRepository);
        this.usersServices = new UsersServices(usersRepository);
    }

    // Get company data with token and post id
    @GetMapping(path = "/get/company/by/{postId}")
    public ResponseEntity<?> getCompanyByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @PathVariable Long postId) {
        try {
            // Have to confirm the user id obtained through the token matches the user id
            // present in the post obtained from the postId
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Optional<Post> post = this.postsServices.getRepository().findById(postId);
                if (post.isPresent()) {
                    if (userId.get() == post.get().get_user().get_user_id()) {
                        return ResponseEntity.ok()
                                .body(this.companyServices.getRepository().findCompanyByPostId(postId));
                    } else {
                        throw new UserIdDoesNotMatchException();
                    }
                } else {
                    throw new PostNotFoundException();
                }
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Create new company using token and post id, along with company model data
    // present in request body
    @PostMapping(path = "/create/company/by/{postId}")
    public ResponseEntity<?> createCompanyByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @PathVariable Long postId,
            @RequestBody Company company) {
        try {
            // Have to confirm the user id obtained through the token matches the user id
            // present in the post obtained from the postId
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Optional<Post> post = this.postsServices.getRepository().findById(postId);
                if (post.isPresent()) {
                    if (userId.get() == post.get().get_user().get_user_id()) {
                        // The requester does not pass in a post attribute for the company, so it must
                        // be added manually.
                        company.set_post(post.get());
                        return ResponseEntity.ok().body(this.companyServices.getRepository().save(company));
                    } else {
                        throw new UserIdDoesNotMatchException();
                    }
                } else {
                    throw new PostNotFoundException();
                }
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body("Null value is invalidating request.");
        } catch (OptimisticLockingFailureException olfe) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(olfe.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Update company data using the token and post id
    @PutMapping(path = "/update/company/by/{postId}")
    public ResponseEntity<?> updateCompanyByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @PathVariable Long postId,
            @RequestBody Company company) {
        try {
            // Have to confirm the user id obtained through the token matches the user id
            // present in the post obtained from the postId
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Optional<Post> post = this.postsServices.getRepository().findById(postId);
                if (post.isPresent()) {
                    if (userId.get() == post.get().get_user().get_user_id()) {
                        Optional<Company> dbCompany = this.companyServices.getRepository().findCompanyByPostId(postId);
                        if (dbCompany.isPresent()) {
                            dbCompany.map(c -> {
                                c.set_company_name(company.get_company_name());
                                c.set_company_website(company.get_company_website());
                                c.set_company_information(company.get_company_information());

                                return this.companyServices.getRepository().save(c);
                            });

                            return ResponseEntity.ok().body(company);
                        } else {
                            throw new CompanyNotFoundException();
                        }
                    } else {
                        throw new UserIdDoesNotMatchException();
                    }
                } else {
                    throw new PostNotFoundException();
                }
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body("Null value is invalidating request.");
        } catch (OptimisticLockingFailureException olfe) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(olfe.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Delete a company using token and post id
    @DeleteMapping(path = "/delete/company/by/{postId}")
    public ResponseEntity<?> deleteCompanyByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @PathVariable Long postId) {
        try {
            // Have to confirm the user id obtained through the token matches the user id
            // present in the post obtained from the postId
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Optional<Post> post = this.postsServices.getRepository().findById(postId);
                if (post.isPresent()) {
                    if (userId.get() == post.get().get_user().get_user_id()) {
                        this.companyServices.getRepository().deleteCompanyByPostId(postId);
                        return ResponseEntity.ok().body("Deleted.");
                    } else {
                        throw new UserIdDoesNotMatchException();
                    }
                } else {
                    throw new PostNotFoundException();
                }
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
