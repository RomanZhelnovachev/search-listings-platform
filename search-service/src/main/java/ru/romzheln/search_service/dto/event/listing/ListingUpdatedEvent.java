package ru.romzheln.search_service.dto.event.listing;


public record ListingUpdatedEvent(String title, String description, String dealType) {}
