package com.jobjournal.JobJournal.controllers.rest;

import java.util.Optional;

import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jobjournal.JobJournal.controllers.rest.ABSTRACT_MUST_EXTEND.RequiredAbstractClassForControllers;
import com.jobjournal.JobJournal.exceptions.handlers.UserIdNotFoundException;
import com.jobjournal.JobJournal.exceptions.handlers.UserProfileOrSettingsNotFoundException;
import com.jobjournal.JobJournal.repositories.SettingRepository;
import com.jobjournal.JobJournal.repositories.UserProfilesRepository;
import com.jobjournal.JobJournal.repositories.UsersRepository;
import com.jobjournal.JobJournal.services.DBTransactionServices;
import com.jobjournal.JobJournal.services.SettingServices;
import com.jobjournal.JobJournal.services.UserProfilesServices;
import com.jobjournal.JobJournal.services.UsersServices;
import com.jobjournal.JobJournal.shared.datastructures.ResponsePayloadHashMap;
import com.jobjournal.JobJournal.shared.helpers.Auth0RequestService;
import com.jobjournal.JobJournal.shared.interfaces.UserWithProfileWithSettingsInterface;
import com.jobjournal.JobJournal.shared.models.auth.Auth0UserInfoResponse;
import com.jobjournal.JobJournal.shared.models.composition.UserWithProfileWithSetting;
import com.jobjournal.JobJournal.shared.models.entity.Setting;
import com.jobjournal.JobJournal.shared.models.entity.UserProfiles;
import com.jobjournal.JobJournal.shared.models.entity.Users;

@RestController
@RequestMapping(path = "/api/users")
@Validated
@CrossOrigin
public class UsersController extends RequiredAbstractClassForControllers {
    // Services
    private final UsersServices usersServices;
    private final DBTransactionServices dbTransactionServices;
    private final UserProfilesServices userProfilesServices;
    private final SettingServices settingServices;

    // Pass in repositories that will work with services
    @Autowired
    public UsersController(UsersRepository usersRepository, UserProfilesRepository userProfilesRepository,
            SettingRepository settingRepository) {
        this.usersServices = new UsersServices(usersRepository);
        this.dbTransactionServices = new DBTransactionServices(usersRepository, userProfilesRepository,
                settingRepository);
        this.userProfilesServices = new UserProfilesServices(userProfilesRepository);
        this.settingServices = new SettingServices(settingRepository);
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

    // Get User Id using Auth0 token
    @GetMapping(path = "/get/userid/by/token")
    public ResponseEntity<?> getUserIdUsingToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                return ResponseEntity.ok().body(new ResponsePayloadHashMap(true, String.valueOf(userId.get()), null)
                        .getResponsePayloadHashMap());
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponsePayloadHashMap(false, e.getMessage(), null).getResponsePayloadHashMap());
        }
    }

    // Create a new user using Auth0 token and requesting Auth0 user id from Auth0
    @PostMapping(path = "/create/newuser/by/token")
    public ResponseEntity<?> createUserUsingToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            String auth0Id = getAuth0IdByToken(token, getAuth0Domain());
            Users newUser = new Users(auth0Id);
            this.usersServices.getRepository().save(newUser);
            return ResponseEntity.ok().body(new ResponsePayloadHashMap(true, "", newUser).getResponsePayloadHashMap());
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponsePayloadHashMap(false, e.getMessage(), null).getResponsePayloadHashMap());
        }
    }

    @PostMapping(path = "/create/newuser/with/profile/with/setting/by/token")
    public ResponseEntity<?> createUserWithProfileWithSettingByToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            Auth0UserInfoResponse userInfo = Auth0RequestService.getAllUserInfoFromAuth0(token, getAuth0Domain());
            Optional<Users> user = this.usersServices.getRepository().findUserByAuth0Id(userInfo.getSub());
            if (user.isPresent()) {
                Optional<UserWithProfileWithSettingsInterface> userWithProfileWithSettings = this.usersServices
                        .getRepository().findUserWithProfileWithSettingByUserId(user.get().get_user_id());
                return ResponseEntity.ok()
                        .body(new ResponsePayloadHashMap(true,
                                "User already exists. Majority of the time, this is considered default behavior.",
                                userWithProfileWithSettings)
                                .getResponsePayloadHashMap());
            } else {
                Users newUser = new Users(userInfo.getSub());
                UserWithProfileWithSetting userWithProfileWithSetting = this.dbTransactionServices
                        .createUserWithProfileWithSetting(newUser,
                                new UserProfiles(newUser, userInfo.getName()), new Setting(newUser));
                return ResponseEntity.ok().body(
                        new ResponsePayloadHashMap(true, "", userWithProfileWithSetting).getResponsePayloadHashMap());
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponsePayloadHashMap(false, e.getMessage(), null).getResponsePayloadHashMap());
        }
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> deleteUserUsingToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            // First remove information from Auth0
            String auth0Id = getAuth0IdByToken(token, getAuth0Domain());
            String response = Auth0RequestService.deleteUserFromFromAuth0(token, getAuth0Domain(), getAuth0Audience());
            System.out.println(response);
            // Next remove information from local db
            Optional<Long> userId = this.usersServices.getRepository().findUserIdByAuth0Id(auth0Id);
            if (userId.isPresent()) {
                Optional<UserProfiles> profile = this.userProfilesServices.getRepository()
                        .findUserProfileByUserId(userId.get());
                Optional<Setting> setting = this.settingServices.getRepository().findSettingByUserId(userId.get());
                if (profile.isPresent() && setting.isPresent()) {
                    this.dbTransactionServices.deleteUserWithProfileWithSetting(userId.get(),
                            profile.get().get_profile_id(), setting.get().get_setting_id());
                    return ResponseEntity.ok()
                            .body(new ResponsePayloadHashMap(true, "User, profile, and settings deleted.", null)
                                    .getResponsePayloadHashMap());
                } else {
                    throw new UserProfileOrSettingsNotFoundException();
                }
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return ResponseEntity.badRequest()
                    .body(new ResponsePayloadHashMap(false, e.getMessage(), null).getResponsePayloadHashMap());
        }
    }

    // For development purposes...
    @GetMapping(path = "/get/token/by/token")
    public ResponseEntity<?> getCurrentUserAuth0Id(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            return ResponseEntity.ok().body(new ResponsePayloadHashMap(true, token, null).getResponsePayloadHashMap());
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponsePayloadHashMap(false, e.getMessage(), null).getResponsePayloadHashMap());
        }
    }
}
