package com.testcase.api.persons.services;

import com.testcase.api.persons.exceptions.PersonNotFoundException;
import com.testcase.api.persons.mappers.PersonMapper;
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
    private final PersonMapper personMapper;

    public List<Person> getAllPersons(final String name, final String surname) {
        return personRepository.findAll(PersonSpecification.filterByParams(name, surname));
    }

    public Person createOne(final Person person) {
        return mergeEntityResult(validatePersonStatus(person));
    }

    public Person getOne(final UUID id) {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    public Person updateOne(final Person updatedPerson) {
        return mergeEntityResult(updatedPerson);
    }

    public void deleteOne(final UUID id) {
        if (!personRepository.existsById(id)) {
            throw new PersonNotFoundException(id);
        }
        personRepository.deleteById(id);
    }


    private Person mergeEntityResult(final Person person) {
        return personRepository.save(person);
    }

}