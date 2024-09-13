package com.testcase.api.persons.validators;

import com.testcase.api.persons.persistence.entities.Person;
import com.testcase.api.persons.provider.models.Gender;


public class GenderValidator {

    public static Person validatePersonStatus(final Person person) {
        if (person.getGender() == null) {
            person.setGender(Gender.NA);
        }
        return person;
    }

}