package ru.romzheln.search_service.exception;

import ru.romzheln.search_service.model.enums.PropertyType;

public class UnknownPropertyType extends RuntimeException {
  public UnknownPropertyType(PropertyType type) {
    super("Неизвестный тип объекта недвижимости - " + type);
  }
}
