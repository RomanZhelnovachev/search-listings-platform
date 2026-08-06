package ru.romzheln.listing.dto.event;

public record LandUseEvent(

        String name,

        String description
) implements OutboxPayload{
}
