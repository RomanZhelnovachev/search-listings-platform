package ru.romzheln.search_service.dto.event.listing;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record ListingCreatedEvent(
    String title,
    String description,
    Long ownerId,
    Long propertyId,
    String dealType,
    BigDecimal price)
     {}
