package ru.romzheln.listing.dto.request.property.apartment;

import ru.romzheln.listing.model.enums.Elevator;
import ru.romzheln.listing.model.enums.Ramp;
import ru.romzheln.listing.model.enums.Side;

import java.math.BigDecimal;

public record ApartmentPhysicalDetailsRequest(

        BigDecimal kitchenSquare,

        Integer floor,

        Elevator elevator,

        Ramp ramp,

        Side side

) {
}
