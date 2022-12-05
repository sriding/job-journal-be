package com.jobjournal.JobJournal.services;

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
}
