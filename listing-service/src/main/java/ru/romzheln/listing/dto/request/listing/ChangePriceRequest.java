package ru.romzheln.listing.dto.request.listing;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ChangePriceRequest(

        @Positive
        BigDecimal newPrice
) {
}
