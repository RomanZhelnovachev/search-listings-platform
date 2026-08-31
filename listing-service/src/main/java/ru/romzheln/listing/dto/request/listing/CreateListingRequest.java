package ru.romzheln.listing.dto.request.listing;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import ru.romzheln.listing.model.enums.DealType;

public record CreateListingRequest(

        @NotBlank
        String title,

        String description,

        @NotNull
        Long ownerId,

        @NotNull
        Long propertyId,

        @NotNull
        DealType dealType,

        @NotNull
        @Positive
        BigDecimal price

) {
}
