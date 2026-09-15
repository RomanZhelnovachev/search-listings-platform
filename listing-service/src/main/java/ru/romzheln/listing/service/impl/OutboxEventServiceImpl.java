package ru.romzheln.listing.service.impl;

import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.romzheln.listing.dto.event.ListingPayload;
import ru.romzheln.listing.dto.event.PropertyPayload;
import ru.romzheln.listing.mapper.OutboxEventMapper;
import ru.romzheln.listing.model.enums.EventType;
import ru.romzheln.listing.model.enums.Region;
import ru.romzheln.listing.model.outbox.OutboxEvent;
import ru.romzheln.listing.repository.OutboxRepository;
import ru.romzheln.listing.service.OutboxEventService;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutboxEventServiceImpl implements OutboxEventService {

    private final OutboxRepository repository;
    private final OutboxEventMapper mapper;


    @Override
    @Transactional()
    public void save(Long aggregateId,
                     Region region,
                     EventType eventType,
                     ListingPayload listingPayload,
                     PropertyPayload propertyPayload) {
        OutboxEvent event = mapper.toEvent(aggregateId, region, eventType, listingPayload, propertyPayload);
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
