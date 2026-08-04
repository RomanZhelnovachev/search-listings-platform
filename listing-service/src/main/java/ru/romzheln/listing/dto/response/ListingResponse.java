package ru.romzheln.listing.dto.response;

import lombok.Builder;
import ru.romzheln.listing.model.enums.DealType;
import ru.romzheln.listing.model.enums.ListingStatus;

import java.math.BigDecimal;

@Builder
public record ListingResponse(

        Long id,

        String title,

        String description,

        ListingStatus status,

        Long ownerId,

        Long propertyId,

        DealType dealType,

        BigDecimal price
) {
}
