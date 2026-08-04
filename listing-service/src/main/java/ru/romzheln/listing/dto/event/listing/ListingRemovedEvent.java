package ru.romzheln.listing.dto.event.listing;

import ru.romzheln.listing.dto.event.OutboxPayload;

import java.time.Instant;

public record ListingRemovedEvent(

        String reason
) implements OutboxPayload {
}
