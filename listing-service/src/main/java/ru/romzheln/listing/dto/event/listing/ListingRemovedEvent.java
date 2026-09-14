package ru.romzheln.listing.dto.event.listing;

import ru.romzheln.listing.dto.event.ListingPayload;

public record ListingRemovedEvent(String reason) implements ListingPayload {}
