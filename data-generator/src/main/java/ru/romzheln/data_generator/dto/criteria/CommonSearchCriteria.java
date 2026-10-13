package ru.romzheln.data_generator.dto.criteria;

import lombok.Builder;
import ru.romzheln.data_generator.enums.DealType;
import ru.romzheln.data_generator.enums.Own;

import java.math.BigDecimal;
import java.util.Set;


@Builder
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
