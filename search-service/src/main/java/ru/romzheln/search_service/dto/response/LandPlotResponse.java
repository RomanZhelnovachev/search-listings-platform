package ru.romzheln.search_service.dto.response;

import ru.romzheln.search_service.dto.response.commonDto.CommonLandDetailsDto;
import ru.romzheln.search_service.dto.response.commonDto.ListingDto;

import java.util.Set;

public record LandPlotResponse(

        ListingDto listing,

        CommonLandDetailsDto commonLandDetails,

        Set<Long> additionalBuildings
) {}
