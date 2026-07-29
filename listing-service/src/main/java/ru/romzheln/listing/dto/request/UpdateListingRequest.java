package ru.romzheln.listing.dto.request;

import jakarta.validation.constraints.NotNull;
import ru.romzheln.listing.model.enums.DealType;

public record UpdateListingRequest(

        @NotNull
        Long id,

        String title,

        String description,

        DealType dealType

) {
}
