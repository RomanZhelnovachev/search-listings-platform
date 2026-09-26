package ru.romzheln.search_service.dto.criteria;

import java.math.BigDecimal;
import java.util.Set;
import ru.romzheln.search_service.model.enums.DealType;
import ru.romzheln.search_service.model.enums.Own;

public record CommonSearchCriteria(

        Boolean hasImage,

        Set<Own> owns,

        BigDecimal squareFrom,

        BigDecimal squareTo,

        Boolean firstOwner,

        Set<DealType> dealTypes,

        BigDecimal priceFrom,

        BigDecimal priceTo,

        Set<Long> mortgageProgramIds,

        Long promotionId
) {}
