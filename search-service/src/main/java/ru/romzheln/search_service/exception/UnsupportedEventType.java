package ru.romzheln.search_service.exception;

import ru.romzheln.search_service.model.enums.AggregateType;
import ru.romzheln.search_service.model.enums.EventType;

public class UnsupportedEventType extends RuntimeException {
  public UnsupportedEventType(EventType eventType, AggregateType aggregateType) {
    super("Неподдерживаемый тип события - " + eventType + " для данного агрегата " + aggregateType);
  }
}
