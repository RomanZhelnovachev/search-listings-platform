package ru.romzheln.search_service.dto.event;

import com.fasterxml.jackson.databind.JsonNode;
import ru.romzheln.search_service.model.enums.AggregateType;
import ru.romzheln.search_service.model.enums.EventType;

import java.time.Instant;
import java.util.UUID;

public record EventMessage(

        UUID eventId,
        AggregateType aggregateType,
        Long aggregateId,
        EventType eventType,
        JsonNode payload,
        Instant createdAt
) {}
