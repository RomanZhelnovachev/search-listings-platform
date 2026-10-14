package ru.romzheln.data_generator.dto.criteria;

import lombok.Builder;
import ru.romzheln.data_generator.enums.Elevator;
import ru.romzheln.data_generator.enums.Ramp;
import ru.romzheln.data_generator.enums.Side;

import java.math.BigDecimal;
import java.util.Set;

@Builder
public record ApartmentPhysicalCriteria(

        BigDecimal kitchenSquareFrom,

        BigDecimal kitchenSquareTo,

        Integer floorFrom,

        Integer floorTo,

        Set<Elevator> elevators,

        Set<Ramp> ramps,

        Set<Side> sides
) {}
