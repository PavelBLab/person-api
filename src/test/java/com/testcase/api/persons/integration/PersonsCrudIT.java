package com.testcase.api.persons.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testcase.api.persons.persistence.repositories.PersonRepository;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;
import java.util.UUID;

import static com.testcase.api.persons.utils.TestDataFactory.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class PersonsCrudIT extends IntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    private ObjectMapper objectMapper; // For converting objects to JSON

    @BeforeEach
    void setUp() {
        personRepository.deleteAll();

        val persons = List.of(PERSON_1, PERSON_2);
        personRepository.saveAll(persons);
    }

    @Test
    void allPersons() throws Exception {
        mockMvc.perform(get("/persons"))
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", hasSize(2)),
                        jsonPath("$[0].name").value("Johnny")
                ).andReturn();
    }

    @ParameterizedTest
    @CsvSource({
            "personNameParam, Louis, Louis, Armstrong",
            "personSurnameParam, Depp, Johnny, Depp"
    })
    void allPersons_UseFiler(String paramName, String paramValue, String name, String surname) throws Exception {
        mockMvc.perform(get("/persons")
                        .param(paramName, paramValue))
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", hasSize(1)),
                        jsonPath("$[0].name").value(name),
                        jsonPath("$[0].surname").value(surname)
                ).andReturn();
    }


    @Test
    void createPerson() throws Exception {
        val personJson = objectMapper.writeValueAsString(PERSON_1);

        mockMvc.perform(
                        post("/persons/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(personJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("name").value("Johnny"),
                        jsonPath("surname").value("Depp")
                ).andReturn();
    }

    @Test
    void onePerson() throws Exception {
        val personId = String.valueOf(personRepository.findAll().get(0).getId());

        mockMvc.perform(get("/persons/{personId}", personId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("name").value("Johnny"),
                        jsonPath("surname").value("Depp")
                ).andReturn();
    }

    @Test
    void onePerson_Throw404() throws Exception {
        val personId = UUID.randomUUID();

        mockMvc.perform(get("/persons/{personId}", personId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.code").value(404),
                        jsonPath("$.message").value("Person with ID " + personId + " not found"))
                .andReturn();
    }

    @Test
    void updatePerson() throws Exception {
        val personId = String.valueOf(personRepository.findAll().get(0).getId());
        val updatedPersonJson = objectMapper.writeValueAsString(UPDATED_PERSON);

        mockMvc.perform(
                        patch("/persons/{personId}", personId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updatedPersonJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("name").value("Nina"),
                        jsonPath("surname").value("Simone")
                ).andReturn();
    }

    @Test
    void updatePerson_Throw404() throws Exception {
        val personId = UUID.randomUUID();
        val updatedPersonJson = objectMapper.writeValueAsString(UPDATED_PERSON);

        mockMvc.perform(
                        patch("/persons/{personId}", personId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updatedPersonJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.code").value(404),
                        jsonPath("$.message").value("Person with ID " + personId + " not found"))
                .andReturn();
    }

    @Test
    void deletePerson() throws Exception {
        val personId = String.valueOf(personRepository.findAll().get(0).getId());

        mockMvc.perform(delete("/persons/{personId}", personId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(status().isNoContent())
                .andReturn();

        mockMvc.perform(get("/persons/{personId}", personId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.code").value(404),
                        jsonPath("$.message").value("Person with ID " + personId + " not found"))
                .andReturn();
    }

    @Test
    void deletePerson_Throw404() throws Exception {
        val personId = UUID.randomUUID();

        mockMvc.perform(delete("/persons/{personId}", personId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.code").value(404),
                        jsonPath("$.message").value("Person with ID " + personId + " not found"))
                .andReturn();
    }

}
