package ru.romzheln.search_service.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.Message;
import ru.romzheln.search_service.dto.event.PurposeEvent;
import ru.romzheln.search_service.exception.UnsupportedEventType;
import ru.romzheln.search_service.mapper.JsonNodeMapper;
import ru.romzheln.search_service.service.PurposeProjectionService;

@Component
@RequiredArgsConstructor
public class PurposeHandler implements Handler{

    private final PurposeProjectionService service;
    private final JsonNodeMapper mapper;

    @Override
    public void handle(Message message) {
        Long purposeId = message.aggregateId();
        PurposeEvent event = mapper.toPurposeEvent(message.propertyPayload());
        switch (message.eventType()){
            case CREATED_PURPOSE -> service.create(purposeId, event);
            case UPDATED_PURPOSE -> service.update(purposeId, event);
            default -> throw new UnsupportedEventType(message.eventType());
        }
    }
}
