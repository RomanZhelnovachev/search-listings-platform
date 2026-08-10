package ru.romzheln.listing.dto.event;

public record DeveloperEvent (

        String name

) implements OutboxPayload {
}
