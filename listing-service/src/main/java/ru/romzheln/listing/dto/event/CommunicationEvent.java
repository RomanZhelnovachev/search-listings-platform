package ru.romzheln.listing.dto.event;

import lombok.Builder;
import ru.romzheln.listing.model.enums.CommunicationType;

@Builder
public record CommunicationEvent(

        CommunicationType type,

        String description
) implements OutboxPayload{
}
