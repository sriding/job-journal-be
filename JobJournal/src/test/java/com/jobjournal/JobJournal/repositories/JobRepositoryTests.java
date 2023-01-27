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
public class JobRepositoryTests {
    @Autowired
    public JobRepository instance;
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
    void givenPostId_whenQueryingDBForJob_thenReturnJob() {
        Optional<Job> job = instance.getJobByPostId(ids.get("post_id"));
        if (job.isPresent() != true) {
            fail("Job not found in database.");
        }
        assertAll("job", () -> assertEquals(job.get().get_job_id(), ids.get("job_id")),
                () -> assertEquals(job.get().get_job_title(), "testing job title"),
                () -> assertEquals(job.get().get_job_information(), ""),
                () -> assertEquals(job.get().get_job_location(), ""),
                () -> assertEquals(job.get().get_job_status(), ""),
                () -> assertEquals(job.get().get_job_type(), ""),
                () -> assertEquals(job.get().get_job_application_submitted_date(), ""),
                () -> assertEquals(job.get().get_job_application_dismissed_date(), ""));
    }

    @Test
    void givenPostId_whenQueryingDBForJobId_thenReturnJobId() {
        Optional<Long> jobId = instance.getJobIdByPostId(ids.get("post_id"));
        if (jobId.isPresent() != true) {
            fail("Job id not found (likely job also does not exist.");
        }
        assertAll("job id", () -> assertEquals(jobId.get(), ids.get("job_id")));
    }

    // This test needs to be run last (order matters)
    // @Test
    // void givenPostId_whenQueryingDBToDeletePost_thenReturnRowsDeleted() {
    // int rowsDeleted = instance.deleteJobByPostId(ids.get("post_id"));
    // if (rowsDeleted == 0) {
    // fail("Nothing was deleted.");
    // }
    // assertAll("rows deleted", () -> assertEquals(rowsDeleted, 1));
    // }
}
