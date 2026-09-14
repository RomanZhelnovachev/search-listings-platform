package ru.romzheln.search_service.dto.event.listing;


import lombok.Builder;

@Builder
public record ListingUpdatedEvent(String title, String description, String dealType) {}
