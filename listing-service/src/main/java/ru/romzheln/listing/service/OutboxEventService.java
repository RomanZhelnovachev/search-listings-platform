package ru.romzheln.listing.service;

import java.util.List;
import ru.romzheln.listing.dto.event.ListingPayload;
import ru.romzheln.listing.dto.event.PropertyPayload;
import ru.romzheln.listing.model.enums.EventType;
import ru.romzheln.listing.model.enums.PropertyType;
import ru.romzheln.listing.model.enums.Region;
import ru.romzheln.listing.model.outbox.OutboxEvent;

public interface OutboxEventService {

  void save(
      Long aggregateId,
      Region region,
      EventType eventType,
      PropertyType propertyType,
      ListingPayload listingPayload,
      PropertyPayload propertyPayload);

  void markAsProcessed(Long id);

  List<OutboxEvent> getNotPublishedEvents();
}
