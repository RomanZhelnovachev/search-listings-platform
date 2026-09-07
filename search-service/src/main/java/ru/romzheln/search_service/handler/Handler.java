package ru.romzheln.search_service.handler;

import ru.romzheln.search_service.dto.event.Message;

public interface Handler {

    void handle(Message message);
}
