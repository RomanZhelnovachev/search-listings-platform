package ru.romzheln.data_generator.dto;

import java.math.BigDecimal;
import lombok.Builder;
import ru.romzheln.data_generator.enums.Elevator;
import ru.romzheln.data_generator.enums.Ramp;
import ru.romzheln.data_generator.enums.Side;


@Builder
public record ApartmentPhysicalDetailsDto(

        BigDecimal kitchenSquare,

        Integer floor,

        Elevator elevator,

        Ramp ramp,

        Side side

) {
}
