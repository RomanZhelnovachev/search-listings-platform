package ru.romzheln.listing.dto.common;

import lombok.Builder;
import ru.romzheln.listing.model.enums.Line;
import ru.romzheln.listing.model.enums.PropertyLocationType;
import ru.romzheln.listing.model.enums.TerritorialZone;

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

        Boolean railwayDeadEnd
) {
}
