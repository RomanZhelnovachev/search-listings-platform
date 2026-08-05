package ru.romzheln.listing.dto.request.property.common;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import ru.romzheln.listing.dto.common.LocationDto;
import ru.romzheln.listing.model.enums.Own;

import java.math.BigDecimal;
import java.util.Set;

@Getter
public abstract class UpdatePropertyRequest {

    private LocationDto locationDto;

    @Positive
    private BigDecimal square;

    private Own own;

    private Boolean firstOwner;

    private Set<Long> communicationIds;
}
