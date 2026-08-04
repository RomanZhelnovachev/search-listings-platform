package ru.romzheln.listing.exception;

public class LandUseNotFoundException extends RuntimeException {
    public LandUseNotFoundException(Long id) {
        super("Назначения земли с ID " + id + " не существует");
    }
}
