package ru.romzheln.listing.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.listing.dto.event.OutboxPayload;
import ru.romzheln.listing.dto.kafka.EventMessage;
import ru.romzheln.listing.model.enums.AggregateType;
import ru.romzheln.listing.model.enums.EventType;
import ru.romzheln.listing.model.outbox.OutboxEvent;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OutboxEventMapper {

    private final ObjectMapper objectMapper;

    public OutboxEvent toEvent(AggregateType type,
                               Long aggregateId,
                               EventType eventType,
                               OutboxPayload payload){
        JsonNode node = objectMapper.valueToTree(payload);
        return OutboxEvent.builder()
                .eventId(UUID.randomUUID())
                .aggregateType(type)
                .aggregateId(aggregateId)
                .eventType(eventType)
                .payload(node)
                .build();
    }

    public EventMessage toEventMessage(OutboxEvent event){
        return EventMessage.builder()
                .eventId(event.getEventId())
                .aggregateType(event.getAggregateType())
                .aggregateId(event.getAggregateId())
                .eventType(event.getEventType())
                .payload(event.getPayload())
                .createdAt(event.getCreatedAt())
                .build();
    }
}
