package ru.romzheln.listing.dto.request.property;

import lombok.Getter;
import ru.romzheln.listing.model.enums.Own;
import ru.romzheln.listing.model.enums.PropertyType;

import java.math.BigDecimal;
import java.util.Set;

@Getter
public abstract class CreatePropertyRequest {

    private PropertyType propertyType;

    private LocationRequest location;

    private BigDecimal square;

    private Own own;

    private Boolean firstOwner;

    private Set<Long> communicationIds;
}
