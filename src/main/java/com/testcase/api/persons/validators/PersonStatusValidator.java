package com.testcase.api.persons.validators;

import com.testcase.api.persons.persistence.entities.Person;
import com.testcase.api.persons.provider.models.PersonStatus;

public class PersonStatusValidator {

    public static Person validatePersonStatus(final Person person) {
        if (person.getStatus() == null) {
            person.setStatus(PersonStatus.READY_FOR_INTERVIEW);
        }
        return person;
    }

}