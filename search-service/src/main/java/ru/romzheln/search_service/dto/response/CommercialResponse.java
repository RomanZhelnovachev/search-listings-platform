package ru.romzheln.search_service.dto.response;

import java.util.Set;
import ru.romzheln.search_service.dto.response.commonDto.CommonPhysicalDetailsDto;
import ru.romzheln.search_service.dto.response.commonDto.ListingDto;
import ru.romzheln.search_service.model.enums.Line;
import ru.romzheln.search_service.model.enums.PropertyLocationType;
import ru.romzheln.search_service.model.enums.TerritorialZone;

public record CommercialResponse(

        ListingDto listing,

        CommonPhysicalDetailsDto commonPhysicalDetails,

        Integer floor,

        Line line,

        PropertyLocationType propertyLocationType,

        TerritorialZone territorialZone,

        Boolean separateEntrance,

        Boolean ventilation,

        Boolean tenantExists,

        Integer entrancesNumber,

        Integer electricalPowerKw,

        Boolean railwayDeadEnd,

        Set<Long> purposeIds
) {}
