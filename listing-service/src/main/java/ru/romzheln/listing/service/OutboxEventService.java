package ru.romzheln.listing.service;

import java.util.List;
import ru.romzheln.listing.dto.event.ListingPayload;
import ru.romzheln.listing.dto.event.PropertyPayload;
import ru.romzheln.listing.model.enums.EventType;
import ru.romzheln.listing.model.outbox.OutboxEvent;

public interface OutboxEventService {

  void save(
      Long aggregateId,
      EventType eventType,
      ListingPayload listingPayload,
      PropertyPayload propertyPayload);

  void markAsProcessed(Long id);

  List<OutboxEvent> getNotPublishedEvents();
}
