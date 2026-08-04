package ru.romzheln.listing.exception;

public class DeveloperNotFoundException extends RuntimeException {
    public DeveloperNotFoundException(Long id) {
        super("Застройщик с ID " + id + " не найден");
    }
}
