package ru.romzheln.search_service.dto.event.listing;

import java.math.BigDecimal;

public record ChangePriceEvent(BigDecimal oldPrice, BigDecimal newPrice) {}
