package ru.romzheln.search_service.exception;

import ru.romzheln.search_service.model.enums.EventType;

public class UnsupportedEventType extends RuntimeException {
  public UnsupportedEventType(EventType eventType) {
    super("Неподдерживаемый тип события - " + eventType + " для данного агрегата ");
  }
}
