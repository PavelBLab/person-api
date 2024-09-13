package com.testcase.api.persons.controllers;

import com.testcase.api.persons.converters.PersonConverter;
import com.testcase.api.persons.provider.controllers.PersonApi;
import com.testcase.api.persons.provider.models.PersonDto;
import com.testcase.api.persons.provider.models.PersonMiniDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class PersonController implements PersonApi {

    private final PersonConverter personConverter;

    @Override
    public ResponseEntity<List<PersonDto>> allPersons(final String personName, final String personSurname) {
        return ResponseEntity.ok(personConverter.getAll(personName, personSurname));
    }

    @Override
    public ResponseEntity<PersonDto> createPerson(final PersonMiniDto personMiniDto) {
        return new ResponseEntity<>(personConverter.createOne(personMiniDto), CREATED);
    }

    @Override
    public ResponseEntity<PersonDto> onePerson(final UUID personId) {
        return ResponseEntity.ok(personConverter.getOne(personId));
    }

    @Override
    public ResponseEntity<PersonDto> updatePerson(final UUID personId, final PersonMiniDto personMiniDto) {
        return personId == null || personMiniDto == null ?
                new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(personConverter.updateOne(personId, personMiniDto), OK);
    }

    @Override
    public ResponseEntity<Void> deletePerson(final UUID personId) {
        personConverter.deleteOne(personId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}