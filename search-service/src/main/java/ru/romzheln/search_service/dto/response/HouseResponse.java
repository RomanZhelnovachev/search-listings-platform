package ru.romzheln.search_service.dto.response;

import ru.romzheln.search_service.dto.response.commonDto.CommonLandDetailsDto;
import ru.romzheln.search_service.dto.response.commonDto.CommonPhysicalDetailsDto;
import ru.romzheln.search_service.dto.response.commonDto.ListingDto;
import ru.romzheln.search_service.model.enums.ConstructionStage;

import java.math.BigDecimal;
import java.util.Set;

public record HouseResponse(

        ListingDto listing,

        CommonPhysicalDetailsDto commonPhysicalDetails,
        
        CommonLandDetailsDto commonLandDetails,

        String developerName,

        String complexName,

        ConstructionStage constructionStage,

        Set<Long> additionalBuildings,

        BigDecimal landPlotSquare
) {}
