package com.testcase.api.persons.utils;

import com.testcase.api.persons.persistence.entities.Person;
import com.testcase.api.persons.provider.models.CountryCode;
import com.testcase.api.persons.provider.models.Gender;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestDataFactory {

    public static final Person PERSON_1 = new Person(UUID.randomUUID(),
            "Johnny",
            "Depp", LocalDate.of(1963, 6, 9),
            "222 Amsterdam Avenue, New York",
            CountryCode.US,
            "Actor",
            BigDecimal.valueOf(150000),
            "Hollywood",
            Gender.MAN);

    public static final Person PERSON_2 = new Person(UUID.randomUUID(),
            "Louis",
            "Armstrong", LocalDate.of(1901, 8, 4),
            "444 Amsterdam Avenue, New York",
            CountryCode.US,
            "Jazz man",
            BigDecimal.valueOf(80000),
            "Broadway",
            Gender.MAN);

    public static final Person UPDATED_PERSON = new Person(UUID.randomUUID(),
            "Nina",
            "Simone", LocalDate.of(1933, 2, 21),
            "333 Amsterdam Avenue, New York",
            CountryCode.US,
            "Singer",
            BigDecimal.valueOf(50000),
            "Broadway",
            Gender.WOMAN);
}
