package com.jobjournal.JobJournal.controllers.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @Autowired
    public CompanyController(CompanyRepository companyRepository, PostRepository postRepository,
            UsersRepository usersRepository) {
        this.companyServices = new CompanyServices(companyRepository);
        this.postsServices = new PostServices(postRepository);
        this.usersServices = new UsersServices(usersRepository);
    }

    @GetMapping(path = "/getbypostid")
    public ResponseEntity<?> getCompanyByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, Long postId) {
        try {
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Optional<Post> post = this.postsServices.getRepository().findById(postId);
                if (post.isPresent()) {
                    if (userId.get() == post.get().getUser().getId()) {
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
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createCompanyByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, Long postId,
            String name, String website, String information) {
        try {
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Optional<Post> post = this.postsServices.getRepository().findById(postId);
                if (post.isPresent()) {
                    if (userId.get() == post.get().getUser().getId()) {
                        Company newCompany = new Company(postId, name, website, information);
                        return ResponseEntity.ok().body(this.companyServices.getRepository().save(newCompany));
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
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PutMapping(path = "/update")
    public ResponseEntity<?> updateCompanyByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, Long postId,
            String name, String website, String information) {
        try {
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Optional<Post> post = this.postsServices.getRepository().findById(postId);
                if (post.isPresent()) {
                    if (userId.get() == post.get().getUser().getId()) {
                        Optional<Company> company = this.companyServices.getRepository().findCompanyByPostId(postId);
                        if (company.isPresent()) {
                            company.map(c -> {
                                c.setName(name);
                                c.setWebsite(website);
                                c.setInformation(information);
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
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> deleteCompanyByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            Long postId) {
        try {
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Optional<Post> post = this.postsServices.getRepository().findById(postId);
                if (post.isPresent()) {
                    this.companyServices.getRepository().deleteCompanyByPostId(postId);
                    return ResponseEntity.ok().body(null);
                } else {
                    throw new PostNotFoundException();
                }
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

}
