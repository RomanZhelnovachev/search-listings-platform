package ru.romzheln.listing.dto.event.listing;

import java.math.BigDecimal;

public record ChangePriceEvent(

        BigDecimal oldPrice,

        BigDecimal newPrice
) {
}
