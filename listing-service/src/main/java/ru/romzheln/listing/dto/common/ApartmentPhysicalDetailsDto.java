package ru.romzheln.listing.dto.common;

import java.math.BigDecimal;
import lombok.Builder;
import ru.romzheln.listing.model.enums.Elevator;
import ru.romzheln.listing.model.enums.Ramp;
import ru.romzheln.listing.model.enums.Side;

@Builder
public record ApartmentPhysicalDetailsDto(

        BigDecimal kitchenSquare,

        Integer floor,

        Elevator elevator,

        Ramp ramp,

        Side side

) {
}
