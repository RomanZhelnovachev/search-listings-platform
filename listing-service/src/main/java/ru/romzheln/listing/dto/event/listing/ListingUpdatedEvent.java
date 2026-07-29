package ru.romzheln.listing.dto.event.listing;

import ru.romzheln.listing.model.enums.DealType;

public record ListingUpdatedEvent(

        String title,

        String description,

        DealType dealType
) {
}
