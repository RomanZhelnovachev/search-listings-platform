package ru.romzheln.search_service.exception;

import ru.romzheln.search_service.model.enums.EventType;

public class UnknownEventType extends RuntimeException {
  public UnknownEventType(EventType type) {
    super("Неизвестный тип события - " + type);
  }
}
