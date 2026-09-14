package ru.romzheln.search_service.dto.event.common;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record ApartmentPhysicalDetailsDto(

        BigDecimal kitchenSquare,

        Integer floor,

        String elevator,

        String ramp,

        String side

) {
}
