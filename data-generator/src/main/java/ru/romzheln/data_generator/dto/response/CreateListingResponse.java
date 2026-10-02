package ru.romzheln.data_generator.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.romzheln.data_generator.enums.DealType;

import java.math.BigDecimal;

public record CreateListingResponse(

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
