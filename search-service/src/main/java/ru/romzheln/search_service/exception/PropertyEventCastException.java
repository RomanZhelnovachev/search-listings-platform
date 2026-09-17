package ru.romzheln.search_service.exception;

import ru.romzheln.search_service.dto.event.property.PropertyEvent;

public class PropertyEventCastException extends RuntimeException {
  public PropertyEventCastException(PropertyEvent event) {
    super("Ошибка приведения типа PropertyEvent - " + event);
  }
}
