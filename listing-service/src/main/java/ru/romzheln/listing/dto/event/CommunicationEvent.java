package ru.romzheln.listing.dto.event;

import ru.romzheln.listing.model.enums.CommunicationType;

public record CommunicationEvent(
        CommunicationType type
) implements PropertyPayload{}
