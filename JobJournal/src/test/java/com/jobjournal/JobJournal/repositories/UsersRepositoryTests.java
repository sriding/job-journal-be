package com.jobjournal.JobJournal.repositories;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jobjournal.JobJournal.services.DBTransactionServices;
import com.jobjournal.JobJournal.shared.interfaces.UserWithProfileWithSettingsInterface;
import com.jobjournal.JobJournal.shared.models.entity.Company;
import com.jobjournal.JobJournal.shared.models.entity.Job;
import com.jobjournal.JobJournal.shared.models.entity.Post;
import com.jobjournal.JobJournal.shared.models.entity.Setting;
import com.jobjournal.JobJournal.shared.models.entity.UserProfiles;
import com.jobjournal.JobJournal.shared.models.entity.Users;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsersRepositoryTests {
    @Autowired
    public UsersRepository instance;
    private HashMap<String, Long> ids = new HashMap<>();

    @BeforeAll
    void setup(@Autowired UsersRepository usersRepository, @Autowired UserProfilesRepository userProfilesRepository,
            @Autowired SettingRepository settingRepository, @Autowired PostRepository postRepository,
            @Autowired JobRepository jobRepository,
            @Autowired CompanyRepository companyRepository) throws Exception {
        DBTransactionServices transactions = new DBTransactionServices(usersRepository, userProfilesRepository,
                settingRepository, postRepository, jobRepository, companyRepository);
        // delete everything in database first
        transactions.truncateAllTablesInDatabase();
        // seed database with information
        Users user = new Users("google-oauth2|110428753866664923333");
        UserProfiles userProfile = new UserProfiles(user, "Stephen Riding");
        Setting setting = new Setting(user);
        Post post = new Post(user, "testing post notes");
        Company company = new Company(post, "testing company name", "", "");
        Job job = new Job(post, "testing job title", "", "", "", "", "", "");
        ids = transactions.seedDatabase(user, userProfile, setting, post, company, job);
    }

    @Test
    void givenAuth0Id_whenQueryingDBForUserId_thenReturnUserId() {
        String auth0Id = "google-oauth2|110428753866664923333";
        Optional<Long> userId = instance.findUserIdByAuth0Id(auth0Id);
        if (!userId.isPresent()) {
            fail("User id not found (likely user does not exist either)");
        }
        assertAll("get user id from auth0 id", () -> assertEquals(userId.get(), ids.get("user_id")));
    }

    @Test
    void givenAuth0Id_whenQueryingDBForUser_thenReturnUser() {
        String auth0Id = "google-oauth2|110428753866664923333";
        Optional<Users> user = instance.findUserByAuth0Id(auth0Id);
        if (!user.isPresent()) {
            fail("User is not present in database.");
        }
        assertAll("user", () -> assertEquals(user.get().get_user_id(), ids.get("user_id")),
                () -> assertEquals(user.get().get_auth0_id(), auth0Id),
                () -> assertEquals(user.get().get_deactivate(), false));
    }

    @Test
    void givenUserId_whenQueryingDBForUserWIthProfileWithSetting_thenReturnUser() {
        Optional<UserWithProfileWithSettingsInterface> userProfileSetting = instance
                .findUserWithProfileWithSettingByUserId(ids.get("user_id"));
        if (!userProfileSetting.isPresent()) {
            fail("User with profile and setting does not exist.");
        }
        assertAll("user with profile with setting",
                () -> assertEquals(userProfileSetting.get().get_user_id(), ids.get("user_id")),
                () -> assertEquals(userProfileSetting.get().get_auth0_id(), "google-oauth2|110428753866664923333"),
                () -> assertEquals(userProfileSetting.get().get_deactivate(), false),
                () -> assertEquals(userProfileSetting.get().get_profile_id(), ids.get("profile_id")),
                () -> assertEquals(userProfileSetting.get().get_profile_name(), "Stephen Riding"),
                () -> assertEquals(userProfileSetting.get().get_setting_id(), ids.get("setting_id")),
                () -> assertEquals(userProfileSetting.get().get_user_id_fk_profile(), ids.get("user_id")),
                () -> assertEquals(userProfileSetting.get().get_user_id_fk_setting(), ids.get("user_id")));
    }
}
