package ru.romzheln.listing.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.listing.dto.event.ListingPayload;
import ru.romzheln.listing.dto.event.PropertyPayload;
import ru.romzheln.listing.dto.kafka.Message;
import ru.romzheln.listing.model.enums.EventType;
import ru.romzheln.listing.model.enums.Region;
import ru.romzheln.listing.model.outbox.OutboxEvent;

@Component
@RequiredArgsConstructor
public class OutboxEventMapper {

  private final ObjectMapper objectMapper;

  public OutboxEvent toEvent(
      Long aggregateId,
      Region region,
      EventType eventType,
      ListingPayload listingPayload,
      PropertyPayload propertyPayload) {
    JsonNode listingNode = objectMapper.valueToTree(listingPayload);
    JsonNode propertyNode = objectMapper.valueToTree(propertyPayload);
    return OutboxEvent.builder()
        .eventId(UUID.randomUUID())
        .aggregateId(aggregateId)
        .region(region)
        .eventType(eventType)
        .listingPayload(listingNode)
        .propertyPayload(propertyNode)
        .build();
  }

  public Message toMessage(OutboxEvent event) {
    return Message.builder()
        .eventId(event.getEventId().toString())
        .aggregateId(event.getAggregateId())
        .eventType(event.getEventType())
        .listingPayload(event.getListingPayload())
        .propertyPayload(event.getPropertyPayload())
        .createdAt(event.getCreatedAt())
        .build();
  }
}
