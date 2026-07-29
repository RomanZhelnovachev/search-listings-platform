package ru.romzheln.listing.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.romzheln.listing.model.enums.AggregateType;
import ru.romzheln.listing.model.enums.EventType;
import ru.romzheln.listing.repository.OutboxRepository;
import ru.romzheln.listing.service.OutboxEventService;

@Service
@RequiredArgsConstructor
public class OutboxEventServiceImpl implements OutboxEventService {

    private final OutboxRepository outboxRepository;


    @Override
    public void save(AggregateType type,
                     Long aggregateId,
                     EventType eventType,
                     Object payload) {

    }
}
