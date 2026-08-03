package ru.romzheln.listing.dto.request.property.common;

import lombok.Getter;
import ru.romzheln.listing.model.enums.Own;

import java.math.BigDecimal;
import java.util.Set;

@Getter
public abstract class UpdatePropertyRequest {

    private Long id;

    private LocationRequest locationRequest;

    private BigDecimal square;

    private Own own;

    private Boolean firstOwner;

    private Set<Long> communicationIds;
}
