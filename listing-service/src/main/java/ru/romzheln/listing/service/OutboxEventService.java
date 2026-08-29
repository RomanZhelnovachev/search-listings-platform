package ru.romzheln.listing.service;

import java.util.List;
import ru.romzheln.listing.dto.event.OutboxPayload;
import ru.romzheln.listing.model.enums.AggregateType;
import ru.romzheln.listing.model.enums.EventType;
import ru.romzheln.listing.model.outbox.OutboxEvent;

public interface OutboxEventService {

    void save(AggregateType type, Long aggregateId, EventType eventType, OutboxPayload payload);

    void markAsProcessed(Long id);

    List<OutboxEvent> getNotPublishedEvents();
}
