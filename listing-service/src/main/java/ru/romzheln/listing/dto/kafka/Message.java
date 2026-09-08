package ru.romzheln.listing.dto.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import ru.romzheln.listing.model.enums.EventType;
import ru.romzheln.listing.model.enums.Region;

import java.time.Instant;

@Builder
public record Message(

        String eventId,

        Long aggregateId,

        Region region,

        EventType eventType,

        JsonNode listingPayload,

        JsonNode propertyPayload,

        Instant createdAt
) {}
