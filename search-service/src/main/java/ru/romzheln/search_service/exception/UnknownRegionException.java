package ru.romzheln.search_service.exception;

public class UnknownRegionException extends RuntimeException {
  public UnknownRegionException(String url) {
    super("Неизвестный регион - " + url);
  }
}
