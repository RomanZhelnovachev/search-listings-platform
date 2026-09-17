package ru.romzheln.search_service.exception;

import ru.romzheln.search_service.model.read_model.ReadModel;

public class ReadModelCastException extends RuntimeException {
  public ReadModelCastException(ReadModel model) {
    super("Ошибка приведения типа ReadModel - " + model);
  }
}
