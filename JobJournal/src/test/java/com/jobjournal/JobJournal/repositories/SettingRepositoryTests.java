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
import com.jobjournal.JobJournal.shared.models.entity.Company;
import com.jobjournal.JobJournal.shared.models.entity.Job;
import com.jobjournal.JobJournal.shared.models.entity.Post;
import com.jobjournal.JobJournal.shared.models.entity.Setting;
import com.jobjournal.JobJournal.shared.models.entity.UserProfiles;
import com.jobjournal.JobJournal.shared.models.entity.Users;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SettingRepositoryTests {
    @Autowired
    private SettingRepository instance;
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
    void givenUserId_whenQueryingDBForSetting_thenReturnSetting() {
        Optional<Setting> settings = instance.findSettingByUserId(ids.get("user_id"));
        if (!settings.isPresent()) {
            fail("Setting is not present in database.");
        }
        assertAll("setting", () -> assertEquals(settings.get().get_setting_id(), ids.get("setting_id")));
    }

    @Test
    void givenUserId_whenQueryingDBForSettingToDelete_thenReturnDeletedRows() {
        int rowsDeleted = instance.deleteSettingByUserId(ids.get("user_id"));
        if (rowsDeleted == 0) {
            fail("No rows were deleted.");
        }
        assertAll("deleted setting", () -> assertEquals(rowsDeleted, 1));
    }
}
