package ru.romzheln.search_service.exception;

import ru.romzheln.search_service.model.embeded.ListingKey;

public class ReadModelAlreadyExistsException extends RuntimeException {
  public ReadModelAlreadyExistsException(ListingKey key) {
    super("ReadModel с ID " + key.getId() + " уже существует");
  }
}
