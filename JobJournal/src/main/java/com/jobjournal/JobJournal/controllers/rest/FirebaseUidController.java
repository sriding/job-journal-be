package com.jobjournal.JobJournal.controllers.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobjournal.JobJournal.shared.helpers.web_client.GetUserInfoFromAuth0;

@RestController
@RequestMapping(path = "/uid")
@CrossOrigin
public class FirebaseUidController {
    @GetMapping(path = "/developer")
    public void showBearer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        System.out.println(token);
    }

    /*
     * @PostMapping(path = "/create")
     * public ResponseEntity<?> createUid(@RequestBody Auth0Uid firebaseUid) {
     * try {
     * Auth0Uid entitySaved = this.repository.save(firebaseUid);
     * return ResponseEntity.ok().body(entitySaved);
     * } catch (IllegalArgumentException ie) {
     * return ResponseEntity.badRequest().body("Firebase Uid cannot be null.");
     * } catch (OptimisticLockingFailureException ofe) {
     * return ResponseEntity.status(HttpStatus.CONFLICT)
     * .body("Database is having issues (Version attribute is different or firebase table not found)."
     * );
     * } catch (Exception e) {
     * return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
     * .body("Server is having issues, try again later or report steps that led to this occuring."
     * );
     * }
     * }
     */

    // Will most likely delete this
    @GetMapping(path = "/get")
    public String getCurrentUserAuth0Id(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @Value("${spring.auth0.domain}") String domain) {
        try {
            return GetUserInfoFromAuth0.getSubFromUserInfoInAuth0(token, domain);
        } catch (Exception e) {
            System.out.println(e);
            return "There was an error retrieving your information.";
        }
    }

    /*
     * @PutMapping(path = "/update")
     * public ResponseEntity<?> updateUid(@RequestBody Auth0Uid firebaseUid,
     * Auth0Uid newFirebaseUid) {
     * try {
     * Optional<Auth0Uid> entityFound =
     * this.repository.findByUid(firebaseUid.getId());
     * if (entityFound.isPresent()) {
     * entityFound.get().setId(newFirebaseUid.getId());
     * return ResponseEntity.ok(this.repository.save(entityFound.get()));
     * } else {
     * return ResponseEntity.badRequest().body("Uid does not exist in system.");
     * }
     * } catch (IllegalArgumentException ie) {
     * return ResponseEntity.badRequest().body("Firebase Uid cannot be null.");
     * } catch (OptimisticLockingFailureException ofe) {
     * return ResponseEntity.status(HttpStatus.CONFLICT)
     * .body("Database is having issues (Version attribute is different or firebase table not found)."
     * );
     * } catch (Exception e) {
     * return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
     * .body("Server is having issues, try again later or report steps that led to this occuring."
     * );
     * }
     * }
     */
}
