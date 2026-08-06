package ru.romzheln.listing.dto.event;

import lombok.Builder;

@Builder
public record PurposeEvent(

        String name,

        String description
) implements OutboxPayload{
}
