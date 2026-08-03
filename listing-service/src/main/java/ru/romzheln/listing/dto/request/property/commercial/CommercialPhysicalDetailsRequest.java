package ru.romzheln.listing.dto.request.property.commercial;

import ru.romzheln.listing.model.enums.Line;
import ru.romzheln.listing.model.enums.PropertyLocationType;
import ru.romzheln.listing.model.enums.TerritorialZone;

public record CommercialPhysicalDetailsRequest(

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
