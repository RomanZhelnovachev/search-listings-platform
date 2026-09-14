package ru.romzheln.listing.dto.event.listing;

import java.math.BigDecimal;
import ru.romzheln.listing.dto.event.ListingPayload;

public record ChangePriceEvent(BigDecimal oldPrice, BigDecimal newPrice) implements ListingPayload {}
