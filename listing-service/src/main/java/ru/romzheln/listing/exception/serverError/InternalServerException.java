package ru.romzheln.listing.exception.serverError;

public abstract class InternalServerException extends RuntimeException {
  public InternalServerException(String message) {
    super(message);
  }
}
