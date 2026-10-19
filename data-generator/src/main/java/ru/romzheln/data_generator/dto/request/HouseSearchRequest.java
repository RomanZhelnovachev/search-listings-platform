package ru.romzheln.data_generator.dto.request;

import lombok.Builder;
import ru.romzheln.data_generator.dto.criteria.CommonLandCriteria;
import ru.romzheln.data_generator.dto.criteria.CommonPhysicalCriteria;
import ru.romzheln.data_generator.dto.criteria.CommonSearchCriteria;
import ru.romzheln.data_generator.enums.ConstructionStage;

import java.math.BigDecimal;
import java.util.Set;

@Builder
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
