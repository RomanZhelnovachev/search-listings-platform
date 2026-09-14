package ru.romzheln.search_service.exception;

import ru.romzheln.search_service.model.embeded.ListingKey;

public class ReadModelNotFoundException extends RuntimeException {
  public ReadModelNotFoundException(ListingKey key) {
    super("ReadModel с ID " + key.getId() + " не найдена");
  }
}
