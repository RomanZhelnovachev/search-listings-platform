package ru.romzheln.listing.dto.event.listing;

import java.math.BigDecimal;
import ru.romzheln.listing.dto.event.OutboxPayload;

public record ChangePriceEvent(BigDecimal oldPrice, BigDecimal newPrice) implements OutboxPayload {}
