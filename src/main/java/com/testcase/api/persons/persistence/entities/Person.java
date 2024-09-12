package com.testcase.api.persons.persistence.entities;

import com.testcase.api.persons.provider.models.PersonGender;
import com.testcase.api.persons.provider.models.PersonStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String country;
    private String jobTitle;
    private String address;

    @Enumerated(EnumType.STRING)
    private PersonStatus status;

    private String employer;

    @Enumerated(EnumType.STRING)
    private PersonGender gender;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(dateOfBirth, person.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateOfBirth);
    }

}