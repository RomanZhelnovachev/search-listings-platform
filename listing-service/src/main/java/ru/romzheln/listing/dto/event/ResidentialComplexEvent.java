package ru.romzheln.listing.dto.event;

public record ResidentialComplexEvent(

        String name
) implements OutboxPayload{
}
