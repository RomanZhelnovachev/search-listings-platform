package ru.romzheln.search_service.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.DeveloperEvent;
import ru.romzheln.search_service.dto.event.EventMessage;
import ru.romzheln.search_service.exception.UnsupportedEventType;
import ru.romzheln.search_service.mapper.JsonNodeMapper;
import ru.romzheln.search_service.service.StateService;

@Component
@RequiredArgsConstructor
public class DeveloperHandler implements Handler{

    private final StateService stateService; 
    private final JsonNodeMapper mapper;

    @Override
    public void handle(EventMessage message) {
        DeveloperEvent event = mapper.toDeveloperEvent(message.payload());
        Long id = message.aggregateId();
        switch (message.eventType()){
            case CREATED -> stateService.saveAndAddDeveloper(id, event);
            case UPDATED -> stateService.updateDeveloper(id, event);
            default -> throw new UnsupportedEventType(message.eventType(), message.aggregateType());
        }
    }
}
