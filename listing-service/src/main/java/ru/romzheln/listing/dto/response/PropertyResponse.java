package ru.romzheln.listing.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.romzheln.listing.dto.common.LocationDto;
import ru.romzheln.listing.model.enums.Own;
import ru.romzheln.listing.model.enums.PropertyType;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public abstract class PropertyResponse{

    private Long id;

    private PropertyType propertyType;

    private LocationDto location;

    private BigDecimal square;

    private Own own;

    private Boolean firstOwner;

    private Set<Long> communicationIds;
}
