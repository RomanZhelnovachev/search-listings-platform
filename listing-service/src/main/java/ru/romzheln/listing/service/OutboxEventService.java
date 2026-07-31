package ru.romzheln.listing.service;

import ru.romzheln.listing.dto.event.OutboxPayload;
import ru.romzheln.listing.model.enums.AggregateType;
import ru.romzheln.listing.model.enums.EventType;

public interface OutboxEventService {

    void save(AggregateType type, Long aggregateId, EventType eventType, OutboxPayload payload);
}
