package com.testcase.api.persons.mappers;

import com.testcase.api.persons.persistence.entities.Person;
import com.testcase.api.persons.provider.models.PersonDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @IterableMapping(qualifiedByName = "mapToShortPersonDto")
    List<PersonDto> mapToShortPersonDtos(final List<Person> persons);

    @Named("mapToShortPersonDto")
    @Mapping(target = "id", ignore = true)
    PersonDto mapToShortPersonDto(final Person persons);

    PersonDto mapToPersonDto(final Person person);

    Person mapToPerson(final PersonDto personDto);

}