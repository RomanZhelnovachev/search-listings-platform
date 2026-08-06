package ru.romzheln.listing.dto.response;

import lombok.Builder;

@Builder
public record PurposeResponse(

        String name,

        String description
) {
}
