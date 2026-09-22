package ru.romzheln.search_service.dto.request;

import ru.romzheln.search_service.dto.criteria.CommonLandCriteria;
import ru.romzheln.search_service.dto.criteria.CommonSearchCriteria;

import java.util.Set;

public record LandPlotSearchRequest(

        CommonSearchCriteria commonSearchCriteria,

        CommonLandCriteria commonLandCriteria,

        Set<Long> additionalBuildingIds
) {}
