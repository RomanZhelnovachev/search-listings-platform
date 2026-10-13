package ru.romzheln.data_generator.dto.request;

import ru.romzheln.data_generator.dto.criteria.CommonLandCriteria;
import ru.romzheln.data_generator.dto.criteria.CommonSearchCriteria;

import java.util.Set;

public record LandPlotSearchRequest(

        CommonSearchCriteria commonSearchCriteria,

        CommonLandCriteria commonLandCriteria,

        Set<Long> additionalBuildingIds
) {}
