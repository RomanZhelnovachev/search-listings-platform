package ru.romzheln.search_service.dto.response;

import java.math.BigDecimal;
import ru.romzheln.search_service.dto.response.commonDto.CommonPhysicalDetailsDto;
import ru.romzheln.search_service.dto.response.commonDto.ListingDto;
import ru.romzheln.search_service.model.enums.ApartmentType;
import ru.romzheln.search_service.model.enums.Elevator;
import ru.romzheln.search_service.model.enums.Ramp;
import ru.romzheln.search_service.model.enums.Side;

public record ApartmentResponse(

        ListingDto listing,

        ApartmentType apartmentType,

        CommonPhysicalDetailsDto commonPhysicalDetails,

        BigDecimal kitchenSquare,

        Integer floor,

        Elevator elevator,

        Ramp ramp,

        Side side,

        String developerName,

        String complexName
) {}
