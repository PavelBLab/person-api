package com.testcase.api.persons.mappers;

import com.testcase.api.persons.persistence.entities.Person;
import com.testcase.api.persons.provider.models.CountryCode;
import com.testcase.api.persons.provider.models.Gender;
import com.testcase.api.persons.provider.models.PersonDto;
import com.testcase.api.persons.provider.models.PersonMiniDto;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @IterableMapping(qualifiedByName = "mapToPersonDto")
    List<PersonDto> mapToPersonDtos(final List<Person> persons);

    @Named("mapToPersonDto")
    PersonDto mapToPersonDto(final Person person);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gender", source = "gender", defaultExpression = "java(com.testcase.api.persons.provider.models.Gender.NA)")
    Person mapToPerson(final PersonMiniDto personMiniDto);

    Person mapToPerson(final PersonDto personDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "dateOfBirth", qualifiedByName = "mapDateOfBirth")
    @Mapping(target = "country", qualifiedByName = "mapCountry")
    @Mapping(target = "annualSalary", qualifiedByName = "mapAnnualSalary")
    @Mapping(target = "gender", qualifiedByName = "mapGender")
    void patchPersonFromMap(final Map<String, String> map,
                             @MappingTarget final Person person);

    @Named("mapDateOfBirth")
    default LocalDate mapDateOfBirth(final String dateOfBirth) {
        return dateOfBirth != null ? LocalDate.parse(dateOfBirth) : null;
    }

    @Named("mapAnnualSalary")
    default BigDecimal mapAnnualSalary(final String annualSalary) {
        return annualSalary != null ? new BigDecimal(annualSalary) : null;
    }

    @Named("mapCountry")
    default CountryCode mapCountry(final String country) {
        return country != null ? CountryCode.valueOf(country) : null;
    }

    @Named("mapGender")
    default Gender mapGender(final String gender) {
        return gender != null ? Gender.valueOf(gender) : Gender.NA;
    }

}