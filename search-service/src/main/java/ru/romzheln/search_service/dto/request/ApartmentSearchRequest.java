package ru.romzheln.search_service.dto.request;

import ru.romzheln.search_service.dto.criteria.ApartmentPhysicalCriteria;
import ru.romzheln.search_service.dto.criteria.CommonPhysicalCriteria;
import ru.romzheln.search_service.dto.criteria.CommonSearchCriteria;
import ru.romzheln.search_service.model.enums.ApartmentType;

import java.util.Set;

public record ApartmentSearchRequest(

        CommonSearchCriteria commonSearchCriteria,

        ApartmentType apartmentType,

        CommonPhysicalCriteria commonPhysicalCriteria,

        ApartmentPhysicalCriteria apartmentPhysicalCriteria,

        Set<Long> developerIds,

        Set<Long> complexIds

) {}
