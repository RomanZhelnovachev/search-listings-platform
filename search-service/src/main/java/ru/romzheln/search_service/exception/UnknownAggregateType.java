package ru.romzheln.search_service.exception;

import ru.romzheln.search_service.model.enums.AggregateType;

public class UnknownAggregateType extends RuntimeException {
  public UnknownAggregateType(AggregateType type) {
    super("Неизвестный тип агрегата - " + type);
  }
}
