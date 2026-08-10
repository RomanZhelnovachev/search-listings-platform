package ru.romzheln.listing.dto.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import ru.romzheln.listing.model.enums.AggregateType;
import ru.romzheln.listing.model.enums.EventType;

import java.time.Instant;
import java.util.UUID;

@Builder
public record EventMessage(

        UUID eventId,

        AggregateType aggregateType,

        Long aggregateId,

        EventType eventType,

        JsonNode payload,

        Instant createdAt
) {
}
