package ru.romzheln.search_service.dto.response.commonDto;

import ru.romzheln.search_service.model.enums.DealType;

import java.math.BigDecimal;
import java.util.Set;

public record ListingDto(

        String title,

        String description,

        Set<Long> imageIds,

        DealType dealType,

        BigDecimal price,

        Set<Long> mortgageProgramIds,

        Long promotionId,

        PropertyDto property
) {}
