package ru.romzheln.listing.dto.response;

import lombok.Builder;

@Builder
public record AdditionalBuildingResponse(

        String name,

        String description
) {
}
