package ru.romzheln.listing.dto.event.listing;

import java.math.BigDecimal;
import lombok.Builder;
import ru.romzheln.listing.dto.event.OutboxPayload;
import ru.romzheln.listing.model.enums.DealType;

@Builder
public record ListingCreatedEvent(
    String title,
    String description,
    Long ownerId,
    Long propertyId,
    DealType dealType,
    BigDecimal price)
    implements OutboxPayload {}
