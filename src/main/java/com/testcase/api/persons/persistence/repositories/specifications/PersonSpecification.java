package com.testcase.api.persons.persistence.repositories.specifications;

import com.testcase.api.persons.persistence.entities.Person;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.springframework.data.jpa.domain.Specification;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonSpecification {

    public static Specification<Person> filterByParams(
        final String name,
        final String surname) {
        return Specification.where(hasAttributeLike("name", name))
            .and(hasAttributeLike("surname", surname));
    }

    private static Specification<Person> hasAttributeLike(final String attribute, final String value) {
        return (personRoot, criteriaQuery, criteriaBuilder) -> value == null ?
            criteriaBuilder.conjunction() :
            criteriaBuilder.like(criteriaBuilder.lower(personRoot.get(attribute)), "%" + value.toLowerCase() + "%");
    }

}