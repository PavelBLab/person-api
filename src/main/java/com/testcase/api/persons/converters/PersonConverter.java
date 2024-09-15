package com.testcase.api.persons.converters;

import com.testcase.api.persons.mappers.PersonMapper;
import com.testcase.api.persons.provider.models.PersonDto;
import com.testcase.api.persons.provider.models.PersonMiniDto;
import com.testcase.api.persons.services.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    public PersonDto createOne(final PersonMiniDto personMiniDto) {
        return personMapper
                .mapToPersonDto(
                        personService.createOne(personMapper.mapToPerson(personMiniDto))
                );
    }

    public PersonDto getOne(final UUID personId) {
        return personMapper.mapToPersonDto(personService.getOne(personId));
    }


    @Transactional
    public PersonDto updateOne(final UUID personId, final PersonMiniDto personMiniDto) {
        val oldPerson = personService.getOne(personId);
        personMapper.updatePersonFromPersonMiniDto(personMiniDto, oldPerson);

        return personMapper.mapToPersonDto(personService.updateOne(oldPerson));
    }

    public void  deleteOne(final UUID id) {
        personService.deleteOne(id);
    }

}