package ru.romzheln.search_service.dto.response.commonDto;

import ru.romzheln.search_service.model.enums.Own;

import java.math.BigDecimal;
import java.util.Set;

public record PropertyDto(

        LocationDto location,

        BigDecimal square,

        Own own,

        Boolean firstOwner,

        Set<Long> communicationIds
) {}
