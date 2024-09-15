package com.testcase.api.persons.mappers;

import com.testcase.api.persons.persistence.entities.Person;
import com.testcase.api.persons.provider.models.PersonDto;
import com.testcase.api.persons.provider.models.PersonMiniDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @IterableMapping(qualifiedByName = "mapToShortPersonDto")
    List<PersonDto> mapToShortPersonDtos(final List<Person> persons);

    @Named("mapToShortPersonDto")
    PersonDto mapToShortPersonDto(final Person persons);

    PersonDto mapToPersonDto(final Person person);

    @Mapping(target = "id", ignore = true)
    Person mapToPerson(final PersonMiniDto personMiniDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updatePersonFromPersonMiniDto(PersonMiniDto personMiniDto, @MappingTarget Person person);

}