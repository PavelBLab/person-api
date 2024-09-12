package com.testcase.api.persons.converters;

import com.testcase.api.persons.mappers.PersonMapper;
import com.testcase.api.persons.provider.models.PersonDto;
import com.testcase.api.persons.provider.models.PersonMiniDto;
import com.testcase.api.persons.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class PersonConverter {

    private final PersonMapper personMapper;
    private final PersonService personService;

    public List<PersonDto> getAll(final String name, final String surname) {
        return personMapper.mapToShortPersonDtos(
                personService.getAllPersons(name, surname)
        );

    }

    public PersonDto createOne(final PersonDto personDto) {
        return personMapper
                .mapToPersonDto(
                        personService.createOne(personMapper.mapToPerson(personDto))
                );
    }

    public PersonDto getOne(final UUID personId) {
        return personMapper.mapToPersonDto(personService.getOne(personId));
    }

    public PersonDto updateOne(final UUID personId, final PersonMiniDto personMiniDto) {
        return personMapper.mapToPersonDto(personService.updateOne(personId, personMiniDto));
    }

}