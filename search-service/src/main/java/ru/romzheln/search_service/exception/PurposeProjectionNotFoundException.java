package ru.romzheln.search_service.exception;

public class PurposeProjectionNotFoundException extends RuntimeException {
  public PurposeProjectionNotFoundException(Long id) {
    super("Проекция цели с ID " + id + " не найдена");
  }
}
