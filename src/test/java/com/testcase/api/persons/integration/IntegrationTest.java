package com.testcase.api.persons.integration;

import com.testcase.api.persons.PersonsApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = PersonsApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("it")
@Testcontainers
public abstract class IntegrationTest {

    @Container
    public static JdbcDatabaseContainer<?> postgres = new PostgreSQLContainer("postgres:16.4-alpine")
            .withDatabaseName("testcase_test")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("init-db.sql");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

}
