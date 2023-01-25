package com.jobjournal.JobJournal.repositories;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

@SpringBootTest
public class CompanyRepositoryTests {
    @Autowired
    private CompanyRepository instance;

    @BeforeAll
    static void setup() {
    }

    @Test
    void testFindCompanyByPostIdQuery() {
        System.out.println(this.instance.findAll(Sort.unsorted()));
    }
}
