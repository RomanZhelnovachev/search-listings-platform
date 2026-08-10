package ru.romzheln.listing.dto.request.reference;

import jakarta.validation.constraints.NotBlank;

public record AdditionalBuildingRequest(

        @NotBlank
        String name,

        String description
) {
}
