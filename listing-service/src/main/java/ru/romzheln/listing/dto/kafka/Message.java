package ru.romzheln.listing.dto.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import ru.romzheln.listing.model.enums.EventType;

import java.time.Instant;

@Builder
public record Message(

        String eventId,

        Long listingId,

        EventType eventType,

        JsonNode listingPayload,

        JsonNode propertyPayload,

        Instant createdAt
) {}
