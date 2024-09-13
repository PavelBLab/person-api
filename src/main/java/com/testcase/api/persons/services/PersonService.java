package com.testcase.api.persons.services;

import com.testcase.api.persons.persistence.entities.Person;
import com.testcase.api.persons.persistence.repositories.PersonRepository;
import com.testcase.api.persons.persistence.repositories.specifications.PersonSpecification;
import com.testcase.api.persons.provider.models.PersonMiniDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.testcase.api.persons.validators.GenderValidator.validatePersonStatus;


@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public List<Person> getAllPersons(final String name, final String surname) {
        return personRepository.findAll(PersonSpecification.filterByParams(name, surname));
    }

    public List<Person> getPersonsByRecordId(final UUID recordId, final String name, final String surname) {
        return personRepository.findAll(PersonSpecification.filterByParams(name, surname));
    }

    public Person createOne(final Person person) {
        return mergeEntityResult(validatePersonStatus(person));
    }

    public Person getOne(final UUID id) {
        return personRepository.findById(id).orElse(Person.builder().build());
    }

    @Transactional
    public Person updateOne(final UUID id, final PersonMiniDto personMiniDto) {

        Person old = getOne(id);

        if (personMiniDto.getName() != null) {
            old.setName(personMiniDto.getName());
        }

        if (personMiniDto.getSurname() != null) {
            old.setSurname(personMiniDto.getSurname());
        }

        if (personMiniDto.getDateOfBirth() != null) {
            old.setDateOfBirth(personMiniDto.getDateOfBirth());
        }

        if (personMiniDto.getAddress() != null) {
            old.setAddress(personMiniDto.getAddress());
        }

        if (personMiniDto.getCountry() != null) {
            old.setCountry(personMiniDto.getCountry());
        }

        if (personMiniDto.getJobTitle() != null) {
            old.setJobTitle(personMiniDto.getJobTitle());
        }

        if (personMiniDto.getAnnualSalary() != null) {
            old.setAnnualSalary(personMiniDto.getAnnualSalary());
        }

        if (personMiniDto.getEmployer() != null) {
            old.setEmployer(personMiniDto.getEmployer());
        }

        if (personMiniDto.getGender() != null) {
            old.setGender(personMiniDto.getGender());
        }


        return mergeEntityResult(old);

    }

    private Person mergeEntityResult(final Person person) {
        return personRepository.save(person);
    }

}