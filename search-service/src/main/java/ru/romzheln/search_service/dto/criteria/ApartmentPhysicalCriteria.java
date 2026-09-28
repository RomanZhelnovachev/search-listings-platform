package ru.romzheln.search_service.dto.criteria;

import ru.romzheln.search_service.model.enums.Elevator;
import ru.romzheln.search_service.model.enums.Ramp;
import ru.romzheln.search_service.model.enums.Side;

import java.math.BigDecimal;
import java.util.Set;

public record ApartmentPhysicalCriteria(

        BigDecimal kitchenSquareFrom,

        BigDecimal kitchenSquareTo,

        Integer floorFrom,

        Integer floorTo,

        Set<Elevator> elevators,

        Set<Ramp> ramps,

        Set<Side> sides
) {}
