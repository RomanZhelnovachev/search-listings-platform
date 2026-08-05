package ru.romzheln.listing.dto.event.listing;

import ru.romzheln.listing.dto.event.OutboxPayload;

import java.util.Set;

public record ImageAddedEvent(

        Set<Long> images
) implements OutboxPayload {
}
