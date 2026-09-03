package ru.romzheln.listing.dto.event.listing;

import ru.romzheln.listing.dto.event.ListingPayload;

import java.util.Set;

public record ImageRemovedEvent(Set<Long> images) implements ListingPayload {}
