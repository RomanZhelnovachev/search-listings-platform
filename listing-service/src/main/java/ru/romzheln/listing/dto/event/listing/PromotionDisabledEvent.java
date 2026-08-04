package ru.romzheln.listing.dto.event.listing;

import ru.romzheln.listing.dto.event.OutboxPayload;

public record PromotionDisabledEvent(

) implements OutboxPayload {
}
