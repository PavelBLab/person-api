package com.testcase.api.persons.services;

import com.testcase.api.persons.exceptions.PersonNotFoundException;
import com.testcase.api.persons.mappers.PersonMapper;
import com.testcase.api.persons.persistence.entities.Person;
import com.testcase.api.persons.persistence.repositories.PersonRepository;
import com.testcase.api.persons.persistence.repositories.specifications.PersonSpecification;
import com.testcase.api.persons.provider.models.PersonDto;
import com.testcase.api.persons.provider.models.PersonMiniDto;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PersonService {

  private final PersonRepository personRepository;
  private final PersonMapper personMapper;

  public List<PersonDto> getAllPersons(final String name, final String surname) {
    val persons = personRepository.findAll(PersonSpecification.filterByParams(name, surname));
    return personMapper.mapToPersonDtos(persons);
  }


  public PersonDto getOne(final UUID id) {
    val person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    return personMapper.mapToPersonDto(person);
  }


  public PersonDto createOne(final PersonMiniDto personMiniDto) {
    val person = personMapper.mapToPerson(personMiniDto);
    val persistedPerson = mergeEntityResult(person);

    return personMapper.mapToPersonDto(persistedPerson);
  }

  @Transactional
  public PersonDto patchOne(final UUID personId, final Map<String, String> map) {
    val oldPerson = personMapper.mapToPerson(getOne(personId));

    if (map != null) {
      personMapper.patchPersonFromMap(map, oldPerson);
    }

    val updatedPerson = mergeEntityResult(oldPerson);

    return personMapper.mapToPersonDto(updatedPerson);
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