package ru.romzheln.search_service.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.CommunicationEvent;
import ru.romzheln.search_service.dto.event.EventMessage;
import ru.romzheln.search_service.exception.UnsupportedEventType;
import ru.romzheln.search_service.mapper.JsonNodeMapper;
import ru.romzheln.search_service.service.CommunicationProjectionService;

@Component
@RequiredArgsConstructor
public class CommunicationHandler implements Handler{

    private final CommunicationProjectionService service;
    private final JsonNodeMapper mapper;

    @Override
    public void handle(EventMessage message) {
        Long id = message.aggregateId();
        CommunicationEvent event = mapper.toCommunicationEvent(message.payload());
        switch (message.eventType()){
            case CREATED -> service.create(id, event);
            case UPDATED -> service.update(id, event);
            case REMOVED -> service.delete(id);
            default -> throw new UnsupportedEventType(message.eventType(), message.aggregateType());
        }
    }
}
