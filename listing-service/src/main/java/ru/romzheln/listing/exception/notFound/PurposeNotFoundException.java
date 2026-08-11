package ru.romzheln.listing.exception.notFound;

public class PurposeNotFoundException extends NotFoundException {
    public PurposeNotFoundException(Long id) {
        super("Назначение с ID " + id + " не найдено");
    }
}
