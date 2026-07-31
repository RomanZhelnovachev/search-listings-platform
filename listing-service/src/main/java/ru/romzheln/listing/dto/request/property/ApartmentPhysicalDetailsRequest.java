package ru.romzheln.listing.dto.request.property;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
