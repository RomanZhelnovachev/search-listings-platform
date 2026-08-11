package ru.romzheln.listing.exception.badRequest;

public abstract class BadRequestException extends RuntimeException {
  public BadRequestException(String message) {
    super(message);
  }
}
