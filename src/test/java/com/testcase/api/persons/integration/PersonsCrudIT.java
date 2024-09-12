package com.testcase.api.persons.integration;

import com.testcase.api.persons.PersonsApplication;
import com.testcase.api.persons.persistence.entities.Person;
import com.testcase.api.persons.persistence.repositories.PersonRepository;
import com.testcase.api.persons.provider.models.PersonGender;
import com.testcase.api.persons.provider.models.PersonStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.UUID;


class PersonsCrudIT extends IntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        personRepository.deleteAll();
        personRepository.save(new Person(UUID.randomUUID(), "Brad", "Pitt", LocalDate.of(1980, 01, 01), "US", "Actor", null, PersonStatus.APPROVED, "Abn-Amro", PersonGender.MAN));
    }

    @Test
    void allPersons() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/persons"))
                .andExpectAll(
                        status().is(200),
                        jsonPath("$", hasSize(1))
                )
                .andReturn();
    }

}
