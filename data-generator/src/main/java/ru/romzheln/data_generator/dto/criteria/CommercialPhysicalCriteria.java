package ru.romzheln.data_generator.dto.criteria;

import lombok.Builder;
import ru.romzheln.data_generator.enums.Line;
import ru.romzheln.data_generator.enums.PropertyLocationType;
import ru.romzheln.data_generator.enums.TerritorialZone;

import java.util.Set;


@Builder
public record CommercialPhysicalCriteria(

        Integer floorFrom,

        Integer floorTo,

        Set<Line> lines,

        Set<PropertyLocationType> propertyLocationTypes,

        Set<TerritorialZone> territorialZones,

        Boolean separateEntrance,

        Boolean ventilation,

        Boolean tenantExists,

        Integer entrancesNumberFrom,

        Integer entrancesNumberTo,

        Integer electricalPowerKwFrom,

        Integer electricalPowerKwTo,

        Boolean railwayDeadEnd
) {}
