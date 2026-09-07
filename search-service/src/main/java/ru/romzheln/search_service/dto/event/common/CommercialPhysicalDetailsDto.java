package ru.romzheln.search_service.dto.event.common;

import lombok.Builder;

@Builder
public record CommercialPhysicalDetailsDto(
    Integer floor,
    String line,
    String propertyLocationType,
    String territorialZone,
    Boolean separateEntrance,
    Boolean ventilation,
    Boolean tenantExists,
    Integer entrancesNumber,
    Integer electricalPowerKw,
    Boolean railwayDeadEnd) {}
