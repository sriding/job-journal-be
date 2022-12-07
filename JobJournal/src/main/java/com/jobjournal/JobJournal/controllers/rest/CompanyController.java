package com.jobjournal.JobJournal.controllers.rest;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
import com.jobjournal.JobJournal.shared.datastructures.ResponsePayloadHashMap;
import com.jobjournal.JobJournal.shared.models.entity.Company;
import com.jobjournal.JobJournal.shared.models.entity.Post;

@RestController
@RequestMapping(path = "/api/company")
@Validated
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ResponsePayloadHashMap tempHM = new ResponsePayloadHashMap();
        tempHM.set_success(false);
        tempHM.set_payload(null);
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            tempHM.getResponsePayloadHashMap().put("_message", errorMessage);
        });

        return ResponseEntity.badRequest().body(tempHM.getResponsePayloadHashMap());
    }

    // Get company data with token and post id
    @GetMapping(path = "/get/company/by/{postId}")
    public ResponseEntity<?> getCompanyByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @PathVariable @Min(-1) Long postId) {
        try {
            // Have to confirm the user id obtained through the token matches the user id
            // present in the post obtained from the postId
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Optional<Post> post = this.postsServices.getRepository().findById(postId);
                if (post.isPresent()) {
                    if (userId.get() == post.get().get_user().get_user_id()) {
                        Optional<Company> company = this.companyServices.getRepository().findCompanyByPostId(postId);
                        if (company.isPresent()) {
                            return ResponseEntity.ok()
                                    .body(new ResponsePayloadHashMap(true, "", company).getResponsePayloadHashMap());
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
            return ResponseEntity.badRequest()
                    .body(new ResponsePayloadHashMap(false, e.getMessage(), null).getResponsePayloadHashMap());
        }
    }

    // Create new company using token and post id, along with company model data
    // present in request body
    @PostMapping(path = "/create/company/by/{postId}", consumes = { "application/json" })
    public ResponseEntity<?> createCompanyByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @PathVariable @Min(-1) Long postId,
            @Valid @RequestBody Company company) {
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
                        // Jsoup for validation/cleaning
                        company.set_company_information(
                                Jsoup.clean(company.get_company_information(), "", Safelist.none(),
                                        new OutputSettings().prettyPrint(false)));
                        company.set_company_name(Jsoup.clean(company.get_company_name(), Safelist.none()));
                        company.set_company_website(Jsoup.clean(company.get_company_website(), Safelist.none()));
                        Company newCompany = this.companyServices.getRepository().save(company);
                        return ResponseEntity.ok()
                                .body(new ResponsePayloadHashMap(true, "", newCompany).getResponsePayloadHashMap());
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
            return ResponseEntity.badRequest()
                    .body(new ResponsePayloadHashMap(false, e.getMessage(), null).getResponsePayloadHashMap());
        }
    }

    // Update company data using the token and post id
    @PutMapping(path = "/update/company/by/{postId}", consumes = { "application/json" })
    public ResponseEntity<?> updateCompanyByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @PathVariable @Min(-1) Long postId,
            @Valid @RequestBody Company company) {
        try {
            // Have to confirm the user id obtained through the token matches the user id
            // present in the post obtained from the postId
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Optional<Post> post = this.postsServices.getRepository().findById(postId);
                if (post.isPresent()) {
                    if (userId.get() == post.get().get_user().get_user_id()) {
                        Optional<Company> dbCompany = this.companyServices.getRepository().findCompanyByPostId(postId);
                        // Jsoup for validation/cleaning
                        if (dbCompany.isPresent()) {
                            dbCompany.map(c -> {
                                c.set_company_name(Jsoup.clean(company.get_company_name(), Safelist.none()));
                                c.set_company_website(Jsoup.clean(company.get_company_website(), Safelist.none()));
                                c.set_company_information(
                                        Jsoup.clean(company.get_company_information(), "", Safelist.none(),
                                                new OutputSettings().prettyPrint(false)));

                                return this.companyServices.getRepository().save(c);
                            });

                            return ResponseEntity.ok()
                                    .body(new ResponsePayloadHashMap(true, "", dbCompany).getResponsePayloadHashMap());
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
            return ResponseEntity.badRequest()
                    .body(new ResponsePayloadHashMap(false, e.getMessage(), null).getResponsePayloadHashMap());
        }
    }

    // Delete a company using token and post id
    @DeleteMapping(path = "/delete/company/by/{postId}")
    public ResponseEntity<?> deleteCompanyByPostId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @PathVariable @Min(-1) Long postId) {
        try {
            // Have to confirm the user id obtained through the token matches the user id
            // present in the post obtained from the postId
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Optional<Post> post = this.postsServices.getRepository().findById(postId);
                if (post.isPresent()) {
                    if (userId.get() == post.get().get_user().get_user_id()) {
                        this.companyServices.getRepository().deleteCompanyByPostId(postId);
                        return ResponseEntity.ok()
                                .body(new ResponsePayloadHashMap(true, "Company succesfully deleted.", null)
                                        .getResponsePayloadHashMap());
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
            return ResponseEntity.badRequest()
                    .body(new ResponsePayloadHashMap(false, e.getMessage(), null).getResponsePayloadHashMap());
        }
    }

}
