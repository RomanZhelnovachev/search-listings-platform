package ru.romzheln.listing.exception.notFound;

public abstract class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
    super(message);
  }
}
