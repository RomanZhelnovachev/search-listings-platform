package ru.romzheln.data_generator.dto.request;

import ru.romzheln.data_generator.dto.criteria.CommercialPhysicalCriteria;
import ru.romzheln.data_generator.dto.criteria.CommonPhysicalCriteria;
import ru.romzheln.data_generator.dto.criteria.CommonSearchCriteria;

import java.util.Set;

public record CommercialSearchRequest(

        CommonSearchCriteria commonSearchCriteria,

        CommonPhysicalCriteria commonPhysicalCriteria,

        CommercialPhysicalCriteria commercialPhysicalCriteria,

        Set<Long> purposeIds
) {}
