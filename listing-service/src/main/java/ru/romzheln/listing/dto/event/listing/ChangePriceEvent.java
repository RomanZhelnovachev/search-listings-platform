package ru.romzheln.listing.dto.event.listing;

import ru.romzheln.listing.dto.event.OutboxPayload;

import java.math.BigDecimal;

public record ChangePriceEvent (

        BigDecimal oldPrice,

        BigDecimal newPrice
) implements OutboxPayload {
}
