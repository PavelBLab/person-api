package com.testcase.api.persons.exceptions;

import java.util.UUID;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(final UUID id) {
        super("Person with ID " + id + " not found");
    }
}
