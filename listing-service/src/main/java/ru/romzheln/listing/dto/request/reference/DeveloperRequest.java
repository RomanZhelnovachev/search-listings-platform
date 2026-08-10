package ru.romzheln.listing.dto.request.reference;

import jakarta.validation.constraints.NotBlank;

public record DeveloperRequest(

        @NotBlank
        String name
) {
}
