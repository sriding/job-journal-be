package com.jobjournal.JobJournal.repositories;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jobjournal.JobJournal.services.DBTransactionServices;
import com.jobjournal.JobJournal.shared.interfaces.PostsWithCompaniesAndJobsInterface;
import com.jobjournal.JobJournal.shared.models.entity.Company;
import com.jobjournal.JobJournal.shared.models.entity.Job;
import com.jobjournal.JobJournal.shared.models.entity.Post;
import com.jobjournal.JobJournal.shared.models.entity.Setting;
import com.jobjournal.JobJournal.shared.models.entity.UserProfiles;
import com.jobjournal.JobJournal.shared.models.entity.Users;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostRepositoryTests {
        @Autowired
        private PostRepository instance;
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
        void givenUserIdStartingIndexEndingIndex_whenQueryingDBForPosts_thenReturnPosts() {
                int startingIndex = 0;
                int endingIndex = 99999;
                List<Post> postList = instance.getPosts(ids.get("user_id"), startingIndex, endingIndex);
                assertAll("get posts", () -> assertEquals(postList.size(), 1),
                                () -> assertEquals(postList.get(0).get_post_id(), ids.get("post_id")),
                                () -> assertEquals(postList.get(0).get_post_notes(), "testing post notes"));
        }

        @Test
        void givenPostId_whenQueryingDBForUserId_thenReturnUserId() {
                Optional<Long> userId = instance.getUserIdFromPostId(ids.get("post_id"));
                if (!userId.isPresent()) {
                        fail("User id not found (likely post does not exist)");
                }
                assertAll("user id", () -> assertEquals(userId.get(), ids.get("user_id")));
        }

        @Test
        void givenUserId_whenQueryingDBForPostsWithCompaniesAndJobsNoStartingIndex_thenReturnPostsWithCompaniesAndJobs() {
                List<PostsWithCompaniesAndJobsInterface> postsList = instance
                                .getPostsWithCompaniesWithJobsNoStartingIndex(ids.get("user_id"));
                assertAll("get joined posts no starting index", () -> assertEquals(postsList.size(), 1),
                                () -> assertEquals(postsList.get(0).get_company_id(), ids.get("company_id")),
                                () -> assertEquals(postsList.get(0).get_company_name(), "testing company name"),
                                () -> assertEquals(postsList.get(0).get_company_information(), ""),
                                () -> assertEquals(postsList.get(0).get_company_website(), ""),
                                () -> assertEquals(postsList.get(0).get_job_id(), ids.get("job_id")),
                                () -> assertEquals(postsList.get(0).get_job_title(), "testing job title"),
                                () -> assertEquals(postsList.get(0).get_job_information(), ""),
                                () -> assertEquals(postsList.get(0).get_job_location(), ""),
                                () -> assertEquals(postsList.get(0).get_job_status(), ""),
                                () -> assertEquals(postsList.get(0).get_job_type(), ""),
                                () -> assertEquals(postsList.get(0).get_job_application_submitted_date(), ""),
                                () -> assertEquals(postsList.get(0).get_job_application_dismissed_date(), ""),
                                () -> assertEquals(postsList.get(0).get_post_id_fk_job(), ids.get("post_id")),
                                () -> assertEquals(postsList.get(0).get_post_id(), ids.get("post_id")),
                                () -> assertEquals(postsList.get(0).get_post_notes(), "testing post notes"),
                                () -> assertEquals(postsList.get(0).get_post_id_fk_company(), ids.get("post_id")),
                                () -> assertEquals(postsList.get(0).get_post_id_fk_job(), ids.get("post_id")),
                                () -> assertEquals(postsList.get(0).get_user_id_fk_post(), ids.get("user_id")));
        }

        @Test
        void givenUserIdPostId_whenQueryingDBForPostsWithCompaniesAndJobsWithStartingIndex_thenReturnPosts() {
                Long postIdUpperLimit = 99999L;
                List<PostsWithCompaniesAndJobsInterface> postsList = instance
                                .getPostsWithCompaniesWithJobsWithStartingIndex(ids.get("user_id"), postIdUpperLimit);
                assertAll("get joined posts with starting index", () -> assertEquals(postsList.size(), 1),
                                () -> assertEquals(postsList.get(0).get_company_id(), ids.get("company_id")),
                                () -> assertEquals(postsList.get(0).get_company_name(), "testing company name"),
                                () -> assertEquals(postsList.get(0).get_company_information(), ""),
                                () -> assertEquals(postsList.get(0).get_company_website(), ""),
                                () -> assertEquals(postsList.get(0).get_job_id(), ids.get("job_id")),
                                () -> assertEquals(postsList.get(0).get_job_title(), "testing job title"),
                                () -> assertEquals(postsList.get(0).get_job_information(), ""),
                                () -> assertEquals(postsList.get(0).get_job_location(), ""),
                                () -> assertEquals(postsList.get(0).get_job_status(), ""),
                                () -> assertEquals(postsList.get(0).get_job_type(), ""),
                                () -> assertEquals(postsList.get(0).get_job_application_submitted_date(), ""),
                                () -> assertEquals(postsList.get(0).get_job_application_dismissed_date(), ""),
                                () -> assertEquals(postsList.get(0).get_post_id_fk_job(), ids.get("post_id")),
                                () -> assertEquals(postsList.get(0).get_post_id(), ids.get("post_id")),
                                () -> assertEquals(postsList.get(0).get_post_notes(), "testing post notes"),
                                () -> assertEquals(postsList.get(0).get_post_id_fk_company(), ids.get("post_id")),
                                () -> assertEquals(postsList.get(0).get_post_id_fk_job(), ids.get("post_id")),
                                () -> assertEquals(postsList.get(0).get_user_id_fk_post(), ids.get("user_id")));
        }

        @Test
        void givenUserIdPostIdAndText_whenQueryingDBForPostsWithCompaniesWithJobsWithStartingIndexWithFilter_thenReturnPosts() {
                Long postIdUpperLimit = 99999L;
                String text = "test";
                List<PostsWithCompaniesAndJobsInterface> postsList = instance
                                .getPostsWithCompaniesWithJobsWithStartingIndexAndWithFilter(ids.get("user_id"),
                                                postIdUpperLimit, text);
                assertAll("get posts starting index and filter", () -> assertEquals(postsList.size(), 1),
                                () -> assertEquals(postsList.get(0).get_company_id(), ids.get("company_id")),
                                () -> assertEquals(postsList.get(0).get_company_name(), "testing company name"),
                                () -> assertEquals(postsList.get(0).get_company_information(), ""),
                                () -> assertEquals(postsList.get(0).get_company_website(), ""),
                                () -> assertEquals(postsList.get(0).get_job_id(), ids.get("job_id")),
                                () -> assertEquals(postsList.get(0).get_job_title(), "testing job title"),
                                () -> assertEquals(postsList.get(0).get_job_information(), ""),
                                () -> assertEquals(postsList.get(0).get_job_location(), ""),
                                () -> assertEquals(postsList.get(0).get_job_status(), ""),
                                () -> assertEquals(postsList.get(0).get_job_type(), ""),
                                () -> assertEquals(postsList.get(0).get_job_application_submitted_date(), ""),
                                () -> assertEquals(postsList.get(0).get_job_application_dismissed_date(), ""),
                                () -> assertEquals(postsList.get(0).get_post_id_fk_job(), ids.get("post_id")),
                                () -> assertEquals(postsList.get(0).get_post_id(), ids.get("post_id")),
                                () -> assertEquals(postsList.get(0).get_post_notes(), "testing post notes"),
                                () -> assertEquals(postsList.get(0).get_post_id_fk_company(), ids.get("post_id")),
                                () -> assertEquals(postsList.get(0).get_post_id_fk_job(), ids.get("post_id")),
                                () -> assertEquals(postsList.get(0).get_user_id_fk_post(), ids.get("user_id")));
        }
}
