package ru.romzheln.listing.dto.request.listing;

import jakarta.validation.constraints.Positive;

public record ChangeListingPromotionRequest(

        @Positive
        Long promotionId
) {
}
