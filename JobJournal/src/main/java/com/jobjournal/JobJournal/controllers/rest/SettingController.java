package com.jobjournal.JobJournal.controllers.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobjournal.JobJournal.controllers.rest.ABSTRACT_MUST_EXTEND.RequiredAbstractClassForControllers;
import com.jobjournal.JobJournal.exceptions.handlers.SettingNotFoundException;
import com.jobjournal.JobJournal.exceptions.handlers.UserIdNotFoundException;
import com.jobjournal.JobJournal.exceptions.handlers.UserNotFoundException;
import com.jobjournal.JobJournal.repositories.SettingRepository;
import com.jobjournal.JobJournal.repositories.UsersRepository;
import com.jobjournal.JobJournal.services.SettingServices;
import com.jobjournal.JobJournal.services.UsersServices;
import com.jobjournal.JobJournal.shared.datastructures.ResponsePayloadHashMap;
import com.jobjournal.JobJournal.shared.models.entity.Setting;
import com.jobjournal.JobJournal.shared.models.entity.Users;

@RestController
@RequestMapping(path = "/api/setting")
@CrossOrigin
public class SettingController extends RequiredAbstractClassForControllers {
    // Services
    private final SettingServices settingServices;
    private final UsersServices usersServices;

    // Add in repositories to work with services
    @Autowired
    public SettingController(SettingRepository settingRepository, UsersRepository usersRepository) {
        this.settingServices = new SettingServices(settingRepository);
        this.usersServices = new UsersServices(usersRepository);
    }

    // Get settings data using token
    @GetMapping(path = "/get/setting/by/token")
    public ResponseEntity<?> getSettingsInformationByUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                Optional<Setting> setting = this.settingServices.getRepository().findSettingByUserId(userId.get());
                if (setting.isPresent()) {
                    return ResponseEntity.ok()
                            .body(new ResponsePayloadHashMap(true, "", setting).getResponsePayloadHashMap());
                } else {
                    throw new SettingNotFoundException();
                }
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponsePayloadHashMap(false, e.getMessage(), null).getResponsePayloadHashMap());
        }
    }

    // Create settings entry using token
    @PostMapping(path = "/create/setting/by/token")
    public ResponseEntity<?> saveSettingsInformationByUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            Optional<Users> user = getUserByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (user.isPresent()) {
                Setting setting = this.settingServices.getRepository().save(new Setting(user.get()));
                return ResponseEntity.ok()
                        .body(new ResponsePayloadHashMap(true, "", setting).getResponsePayloadHashMap());
            } else {
                throw new UserNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponsePayloadHashMap(false, e.getMessage(), null).getResponsePayloadHashMap());
        }
    }

    // Delete settings entry using token
    @DeleteMapping(path = "/delete/setting/by/token")
    public ResponseEntity<?> deleteSettingsInformationByUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            Optional<Long> userId = getUserIdByToken(token, getAuth0Domain(), this.usersServices.getRepository());
            if (userId.isPresent()) {
                int rowsDeleted = this.settingServices.getRepository().deleteSettingByUserId(userId.get());
                if (rowsDeleted >= 1) {
                    return ResponseEntity.ok()
                            .body(new ResponsePayloadHashMap(true, "", null).getResponsePayloadHashMap());
                } else {
                    throw new SettingNotFoundException();
                }
            } else {
                throw new UserIdNotFoundException();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponsePayloadHashMap(false, e.getMessage(), null).getResponsePayloadHashMap());
        }
    }

    // TODO: Updating Settings

}
