package ru.romzheln.listing.dto.response;

import lombok.Builder;

@Builder
public record LandUseResponse(

        String name,

        String description
) {
}
