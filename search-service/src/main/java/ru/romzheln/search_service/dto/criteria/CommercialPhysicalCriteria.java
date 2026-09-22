package ru.romzheln.search_service.dto.criteria;

import ru.romzheln.search_service.model.enums.Line;
import ru.romzheln.search_service.model.enums.PropertyLocationType;
import ru.romzheln.search_service.model.enums.TerritorialZone;

import java.util.Set;

public record CommercialPhysicalCriteria(

        Integer floorFrom,

        Integer floorTo,

        Set<Line> lines,

        Set<PropertyLocationType> propertyLocationTypes,

        Set<TerritorialZone> territorialZones,

        Boolean separateEntrance,

        Boolean tenantExists,

        Integer entrancesNumberFrom,

        Integer entrancesNumberTo,

        Integer electricalPowerKwFrom,

        Integer electricalPowerKwTo,

        Boolean railwayDeadEnd
) {}
