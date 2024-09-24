package com.testcase.api.persons.controllers;

import com.testcase.api.persons.provider.controllers.PersonApi;
import com.testcase.api.persons.provider.models.PersonDto;
import com.testcase.api.persons.provider.models.PersonMiniDto;
import com.testcase.api.persons.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class PersonController implements PersonApi {

    private final PersonService personService;

    @Override
    public ResponseEntity<List<PersonDto>> allPersons(final String personName, final String personSurname) {
        return ResponseEntity.ok(personService.getAllPersons(personName, personSurname));
    }

    @Override
    public ResponseEntity<PersonDto> onePerson(final UUID personId) {
        return ResponseEntity.ok(personService.getOne(personId));
    }

    @Override
    public ResponseEntity<PersonDto> createPerson(final PersonMiniDto personMiniDto) {
        return new ResponseEntity<>(personService.createOne(personMiniDto), CREATED);
    }

    @Override
    public ResponseEntity<PersonDto> patchPerson(final UUID personId, final Map<String, String> map) {
        return personId == null || map == null ?
                new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(personService.patchOne(personId, map), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deletePerson(final UUID personId) {
        personService.deleteOne(personId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}