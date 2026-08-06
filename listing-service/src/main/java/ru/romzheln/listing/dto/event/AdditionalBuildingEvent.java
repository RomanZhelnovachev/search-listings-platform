package ru.romzheln.listing.dto.event;

public record AdditionalBuildingEvent (

        String name,

        String description
) implements OutboxPayload{
}
