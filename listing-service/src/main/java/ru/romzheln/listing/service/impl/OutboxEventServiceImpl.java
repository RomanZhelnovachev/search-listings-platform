package ru.romzheln.listing.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.romzheln.listing.dto.event.OutboxPayload;
import ru.romzheln.listing.dto.kafka.EventMessage;
import ru.romzheln.listing.kafka.EventProducer;
import ru.romzheln.listing.mapper.OutboxEventMapper;
import ru.romzheln.listing.model.enums.AggregateType;
import ru.romzheln.listing.model.enums.EventType;
import ru.romzheln.listing.model.outbox.OutboxEvent;
import ru.romzheln.listing.repository.OutboxRepository;
import ru.romzheln.listing.service.OutboxEventService;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutboxEventServiceImpl implements OutboxEventService {

    private final OutboxRepository repository;
    private final OutboxEventMapper mapper;
    private final EventProducer producer;


    @Override
    @Transactional()
    public void save(AggregateType type,
                     Long aggregateId,
                     EventType eventType,
                     OutboxPayload payload) {
        OutboxEvent event = mapper.toEvent(type, aggregateId, eventType, payload);
        OutboxEvent savedEvent = repository.save(event);
        log.info("Событие с ID {} успешно сохранено", savedEvent.getId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markAsProcessed(Long id) {
        repository.findById(id).ifPresent(event ->
    event.setProcessedAt(Instant.now()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OutboxEvent> getNotPublishedEvents() {
        return repository.findTop100ByProcessedAtIsNullOrderByCreatedAtAsc();
    }
}
