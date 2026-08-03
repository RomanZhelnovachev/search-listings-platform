package ru.romzheln.listing.exception;

public class PurposeNotFoundException extends RuntimeException {
    public PurposeNotFoundException(Long id) {
        super("Назначение с ID " + id + " не найдено");
    }
}
