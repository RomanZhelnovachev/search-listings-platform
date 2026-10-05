package ru.romzheln.data_generator.dto;

import lombok.Builder;
import ru.romzheln.data_generator.enums.Line;
import ru.romzheln.data_generator.enums.PropertyLocationType;
import ru.romzheln.data_generator.enums.TerritorialZone;


@Builder
public record CommercialPhysicalDetailsDto(
    Integer floor,
    Line line,
    PropertyLocationType propertyLocationType,
    TerritorialZone territorialZone,
    Boolean separateEntrance,
    Boolean ventilation,
    Boolean tenantExists,
    Integer entrancesNumber,
    Integer electricalPowerKw,
    Boolean railwayDeadEnd) {}
