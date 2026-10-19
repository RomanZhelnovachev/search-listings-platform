package ru.romzheln.data_generator.dto.request;

import lombok.Builder;
import ru.romzheln.data_generator.dto.criteria.ApartmentPhysicalCriteria;
import ru.romzheln.data_generator.dto.criteria.CommonPhysicalCriteria;
import ru.romzheln.data_generator.dto.criteria.CommonSearchCriteria;
import ru.romzheln.data_generator.enums.ApartmentType;

import java.util.Set;


@Builder
public record ApartmentSearchRequest(

        CommonSearchCriteria commonSearchCriteria,

        Set<ApartmentType> apartmentTypes,

        CommonPhysicalCriteria commonPhysicalCriteria,

        ApartmentPhysicalCriteria apartmentPhysicalCriteria,

        Set<Long> developerIds,

        Set<Long> complexIds

) {}
