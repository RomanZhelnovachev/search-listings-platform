package ru.romzheln.listing.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.romzheln.listing.kafka.EventProducer;
import ru.romzheln.listing.mapper.OutboxEventMapper;
import ru.romzheln.listing.model.outbox.OutboxEvent;
import ru.romzheln.listing.service.EventPublisher;
import ru.romzheln.listing.service.OutboxEventService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventPublisherImpl implements EventPublisher {

    private final OutboxEventMapper mapper;
    private final EventProducer producer;
    private final OutboxEventService service;

    @Override
    public void publish() {
        List<OutboxEvent> events = service.getNotPublishedEvents();
        if(events.isEmpty()){
            log.debug("Нет новых событий для публикации");
            return;
        }
        for(OutboxEvent event : events){
            producer.send(mapper.toEventMessage(event))
                    .thenRun(() -> service.markAsProcessed(event.getId()))
                    .exceptionally(ex -> {
                        log.error(
                                "Ошибка публикации события {}",
                                event.getId(),
                                ex
                        );
                        return null;
                    });
        }
    }
}
