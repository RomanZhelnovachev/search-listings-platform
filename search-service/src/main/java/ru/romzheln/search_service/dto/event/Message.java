package ru.romzheln.search_service.dto.event;

import com.fasterxml.jackson.databind.JsonNode;
import java.time.Instant;
import lombok.Builder;
import ru.romzheln.search_service.model.enums.EventType;

@Builder
public record Message(

        String eventId,

        Long aggregateId,

        EventType eventType,

        JsonNode listingPayload,

        JsonNode propertyPayload,

        Instant createdAt
) {}
