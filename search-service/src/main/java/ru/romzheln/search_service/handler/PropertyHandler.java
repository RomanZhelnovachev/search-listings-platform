package ru.romzheln.search_service.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.EventMessage;

@Component
@RequiredArgsConstructor
public class PropertyHandler implements Handler{

    @Override
    public void handle(EventMessage message) {

    }
}
