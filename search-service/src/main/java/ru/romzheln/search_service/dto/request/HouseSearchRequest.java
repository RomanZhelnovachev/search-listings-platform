package ru.romzheln.search_service.dto.request;

import ru.romzheln.search_service.dto.criteria.CommonLandCriteria;
import ru.romzheln.search_service.dto.criteria.CommonPhysicalCriteria;
import ru.romzheln.search_service.dto.criteria.CommonSearchCriteria;
import ru.romzheln.search_service.model.enums.ConstructionStage;

import java.math.BigDecimal;
import java.util.Set;

public record HouseSearchRequest(

        CommonSearchCriteria commonSearchCriteria,

        CommonPhysicalCriteria commonPhysicalCriteria,

        CommonLandCriteria commonLandCriteria,

        Set<Long> developerIds,

        Set<Long> complexIds,

        Set<ConstructionStage> constructionStages,

        Set<Long> additionalBuildingIds,

        BigDecimal landPlotSquareFrom,

        BigDecimal landPlotSquareTo
) {}
