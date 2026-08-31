package ru.romzheln.search_service.handler;

import ru.romzheln.search_service.dto.event.EventMessage;

public interface Handler {

    void handle(EventMessage message);
}
