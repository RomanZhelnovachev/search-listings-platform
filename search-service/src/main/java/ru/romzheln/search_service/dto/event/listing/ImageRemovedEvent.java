package ru.romzheln.search_service.dto.event.listing;

import java.util.Set;

public record ImageRemovedEvent(Set<Long> images){}
