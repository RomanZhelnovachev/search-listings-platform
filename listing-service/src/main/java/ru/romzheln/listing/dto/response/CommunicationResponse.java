package ru.romzheln.listing.dto.response;

import lombok.Builder;
import ru.romzheln.listing.model.enums.CommunicationType;

@Builder
public record CommunicationResponse(

        CommunicationType type,

        String description
) {
}
