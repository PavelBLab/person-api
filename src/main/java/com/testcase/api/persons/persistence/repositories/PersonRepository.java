package com.testcase.api.persons.persistence.repositories;

import com.testcase.api.persons.persistence.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID>, JpaSpecificationExecutor<Person> {}