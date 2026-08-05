package ru.romzheln.listing.dto.event.property;

import lombok.Getter;
import lombok.Setter;
import ru.romzheln.listing.dto.common.LocationDto;
import ru.romzheln.listing.dto.event.OutboxPayload;
import ru.romzheln.listing.model.enums.Own;
import ru.romzheln.listing.model.enums.PropertyType;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public abstract class PropertyEvent implements OutboxPayload {

    private PropertyType propertyType;

    private LocationDto location;

    private BigDecimal square;

    private Own own;

    private Boolean firstOwner;

    private Set<Long> communicationIds;
}
