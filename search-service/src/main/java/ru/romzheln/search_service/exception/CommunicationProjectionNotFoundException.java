package ru.romzheln.search_service.exception;

public class CommunicationProjectionNotFoundException extends RuntimeException {
  public CommunicationProjectionNotFoundException(Long id) {
    super("Коммуникация с ID " + id + " не найдена");
  }
}
