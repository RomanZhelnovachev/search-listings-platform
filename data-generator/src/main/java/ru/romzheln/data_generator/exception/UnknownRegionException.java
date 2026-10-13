package ru.romzheln.data_generator.exception;

public class UnknownRegionException extends RuntimeException {
  public UnknownRegionException(String url) {
    super("Неизвестный регион - " + url);
  }
}
