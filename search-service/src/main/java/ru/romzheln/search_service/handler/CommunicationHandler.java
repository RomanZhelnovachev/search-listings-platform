package ru.romzheln.search_service.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.CommunicationEvent;
import ru.romzheln.search_service.dto.event.Message;
import ru.romzheln.search_service.exception.UnsupportedEventType;
import ru.romzheln.search_service.mapper.ProjectionMapper;
import ru.romzheln.search_service.service.CommunicationProjectionService;

@Component
@RequiredArgsConstructor
public class CommunicationHandler implements Handler{

    private final CommunicationProjectionService service;
    private final ProjectionMapper mapper;

    @Override
    public void handle(Message message) {
        Long communicationId = message.aggregateId();
        CommunicationEvent event = mapper.toCommunicationEvent(message.propertyPayload());
        switch (message.eventType()){
            case CREATED_COMMUNICATION -> service.create(communicationId, event);
            case UPDATED_COMMUNICATION -> service.update(communicationId, event);
            default -> throw new UnsupportedEventType(message.eventType());
        }
    }
}
