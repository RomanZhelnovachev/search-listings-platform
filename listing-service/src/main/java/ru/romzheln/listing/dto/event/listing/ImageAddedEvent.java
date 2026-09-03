package ru.romzheln.listing.dto.event.listing;

import java.util.Set;
import ru.romzheln.listing.dto.event.ListingPayload;

public record ImageAddedEvent(Set<Long> images) implements ListingPayload {}
