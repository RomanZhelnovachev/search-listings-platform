package ru.romzheln.data_generator.dto.request;

import lombok.Builder;
import ru.romzheln.data_generator.dto.criteria.CommercialPhysicalCriteria;
import ru.romzheln.data_generator.dto.criteria.CommonPhysicalCriteria;
import ru.romzheln.data_generator.dto.criteria.CommonSearchCriteria;

import java.util.Set;

@Builder
public record CommercialSearchRequest(

        CommonSearchCriteria commonSearchCriteria,

        CommonPhysicalCriteria commonPhysicalCriteria,

        CommercialPhysicalCriteria commercialPhysicalCriteria,

        Set<Long> purposeIds
) {}
