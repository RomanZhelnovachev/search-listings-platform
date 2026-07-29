package ru.romzheln.listing.dto.event.listing;

import lombok.Builder;
import ru.romzheln.listing.model.enums.DealType;

import java.math.BigDecimal;

@Builder
public record ListingCreatedEvent(

        String title,

        String description,

        Long ownerId,

        Long propertyId,

        DealType dealType,

        BigDecimal price

) {
}
