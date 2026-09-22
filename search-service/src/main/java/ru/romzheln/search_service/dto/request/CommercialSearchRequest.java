package ru.romzheln.search_service.dto.request;

import ru.romzheln.search_service.dto.criteria.CommercialPhysicalCriteria;
import ru.romzheln.search_service.dto.criteria.CommonPhysicalCriteria;
import ru.romzheln.search_service.dto.criteria.CommonSearchCriteria;

import java.util.Set;

public record CommercialSearchRequest(

        CommonSearchCriteria commonSearchCriteria,

        CommonPhysicalCriteria commonPhysicalCriteria,

        CommercialPhysicalCriteria commercialPhysicalCriteria,

        Set<Long> purposeIds
) {}
