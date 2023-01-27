package com.jobjournal.JobJournal.services;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jobjournal.JobJournal.repositories.CompanyRepository;
import com.jobjournal.JobJournal.repositories.JobRepository;
import com.jobjournal.JobJournal.repositories.PostRepository;
import com.jobjournal.JobJournal.repositories.SettingRepository;
import com.jobjournal.JobJournal.repositories.UserProfilesRepository;
import com.jobjournal.JobJournal.repositories.UsersRepository;
import com.jobjournal.JobJournal.shared.models.composition.PostsWithCompaniesAndJobs;
import com.jobjournal.JobJournal.shared.models.composition.UserWithProfileWithSetting;
import com.jobjournal.JobJournal.shared.models.entity.Company;
import com.jobjournal.JobJournal.shared.models.entity.Job;
import com.jobjournal.JobJournal.shared.models.entity.Post;
import com.jobjournal.JobJournal.shared.models.entity.Setting;
import com.jobjournal.JobJournal.shared.models.entity.UserProfiles;
import com.jobjournal.JobJournal.shared.models.entity.Users;

public class DBTransactionServices {
    private PostRepository postRepository;
    private JobRepository jobRepository;
    private CompanyRepository companyRepository;
    private UsersRepository usersRepository;
    private UserProfilesRepository userProfilesRepository;
    private SettingRepository settingRepository;

    @Autowired
    public DBTransactionServices(PostRepository postRepository, JobRepository jobRepository,
            CompanyRepository companyRepository) {
        this.postRepository = postRepository;
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
    }

    @Autowired
    public DBTransactionServices(UsersRepository usersRepository, UserProfilesRepository userProfilesRepository,
            SettingRepository settingRepository) {
        this.usersRepository = usersRepository;
        this.userProfilesRepository = userProfilesRepository;
        this.settingRepository = settingRepository;
    }

    @Autowired
    public DBTransactionServices(UsersRepository usersRepository, UserProfilesRepository userProfilesRepository,
            SettingRepository settingRepository, PostRepository postRepository, JobRepository jobRepository,
            CompanyRepository companyRepository) {
        this.usersRepository = usersRepository;
        this.userProfilesRepository = userProfilesRepository;
        this.settingRepository = settingRepository;
        this.postRepository = postRepository;
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public void deletePostWithCompanyWithJob(Long postId, Long companyId, Long jobId) {
        this.companyRepository.deleteById(companyId);
        this.jobRepository.deleteById(jobId);
        this.postRepository.deleteById(postId);
    }

    @Transactional
    public void deleteUserWithProfileWithSetting(Long userId, Long profileId, Long settingId) {
        this.settingRepository.deleteById(settingId);
        this.userProfilesRepository.deleteById(profileId);
        this.usersRepository.deleteById(userId);
    }

    @Transactional
    public UserWithProfileWithSetting createUserWithProfileWithSetting(Users user, UserProfiles userProfiles,
            Setting setting) {
        this.usersRepository.save(user);
        this.userProfilesRepository.save(userProfiles);
        this.settingRepository.save(setting);

        return new UserWithProfileWithSetting(user, userProfiles, setting);
    }

    @Transactional
    public PostsWithCompaniesAndJobs createPostWithCompanyWithJob(Post post, Company company, Job job) {
        this.postRepository.save(post);
        this.companyRepository.save(company);
        this.jobRepository.save(job);

        return new PostsWithCompaniesAndJobs(post, company, job);
    }

    @Transactional
    public void truncateAllTablesInDatabase() {
        this.companyRepository.deleteAll();
        this.jobRepository.deleteAll();
        this.postRepository.deleteAll();
        this.userProfilesRepository.deleteAll();
        this.settingRepository.deleteAll();
        this.usersRepository.deleteAll();
    }

    @Transactional
    public HashMap<String, Long> seedDatabase(Users user, UserProfiles userProfile, Setting setting, Post post,
            Company company,
            Job job) {
        Users savedUser = this.usersRepository.save(user);
        userProfile.set_user(savedUser);
        UserProfiles savedProfile = this.userProfilesRepository.save(userProfile);
        setting.set_user(savedUser);
        Setting savedSetting = this.settingRepository.save(setting);
        post.set_user(savedUser);
        Post savedPost = this.postRepository.save(post);
        company.set_post(savedPost);
        Company savedCompany = this.companyRepository.save(company);
        job.set_post(savedPost);
        Job savedJob = this.jobRepository.save(job);

        return new HashMap<String, Long>() {
            {
                put("user_id", savedUser.get_user_id());
                put("profile_id", savedProfile.get_profile_id());
                put("setting_id", savedSetting.get_setting_id());
                put("post_id", savedPost.get_post_id());
                put("company_id", savedCompany.get_company_id());
                put("job_id", savedJob.get_job_id());
            }
        };
    }
}
