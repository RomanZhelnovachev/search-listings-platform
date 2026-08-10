package ru.romzheln.listing.dto.request.reference;

import ru.romzheln.listing.model.enums.CommunicationType;

public record CommunicationRequest(

        CommunicationType type,

        String description
) {
}
