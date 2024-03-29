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
public class CompanyRepositoryTests {
    @Autowired
    private CompanyRepository instance;
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
    void givenPostId_whenQueryingDBForCompany_thenReturnCompany() {
        Optional<Company> company = instance.findCompanyByPostId(ids.get("post_id"));
        if (!company.isPresent()) {
            fail("Company is not present in db.");
        }
        assertAll("company", () -> assertEquals(company.get().get_company_id(), ids.get("company_id")),
                () -> assertEquals(company.get().get_company_name(), "testing company name"),
                () -> assertEquals(company.get().get_company_website(), ""),
                () -> assertEquals(company.get().get_company_information(), ""));
    }

    @Test
    void givenPostId_whenQueryingDBForCompanyId_thenReturnCompanyId() {
        Optional<Long> companyId = instance.findCompanyIdByPostId(ids.get("post_id"));
        if (!companyId.isPresent()) {
            fail("Company id not present in db (company probably isn't either).");
        }
        assertAll("company id", () -> assertEquals(companyId.get(), ids.get("company_id")));
    }

    @Test
    void givenPostId_whenQueryingDBToDeleteCompany_thenReturnRowsDeleted() {
        int rowsDeleted = instance.deleteCompanyByPostId(ids.get("post_id"));
        if (rowsDeleted == 0) {
            fail("No company was deleted.");
        }
        assertAll("", () -> assertEquals(rowsDeleted, 1));
    }
}
