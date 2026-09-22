package ru.romzheln.search_service.dto.criteria;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

import ru.romzheln.search_service.model.enums.DealType;
import ru.romzheln.search_service.model.enums.Region;

public record CommonSearchCriteria(

        @NotNull
        Region region,

        Boolean hasImage,

        BigDecimal squareFrom,

        BigDecimal squareTo,

        Boolean firstOwner,

        Set<DealType> dealTypes,

        BigDecimal priceFrom,

        BigDecimal priceTo,

        Set<Long> mortgageProgramIds,

        Long promotionId
) {}
