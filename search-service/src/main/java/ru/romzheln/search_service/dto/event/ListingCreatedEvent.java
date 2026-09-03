package ru.romzheln.search_service.dto.event;

import java.math.BigDecimal;

public record ListingCreatedEvent(

        String title,

        String description,

        Long ownerId,

        Long propertyId,

        String propertyType,

        String dealType,

        BigDecimal price
) {}
