package ru.romzheln.listing.dto.event.listing;

import java.math.BigDecimal;
import lombok.Builder;
import ru.romzheln.listing.dto.event.ListingPayload;
import ru.romzheln.listing.model.enums.DealType;
import ru.romzheln.listing.model.enums.PropertyType;

@Builder
public record ListingCreatedEvent(
    String title,
    String description,
    Long ownerId,
    Long propertyId,
    PropertyType propertyType,
    DealType dealType,
    BigDecimal price)
    implements ListingPayload {}
