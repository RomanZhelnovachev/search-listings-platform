package ru.romzheln.listing.exception.notFound;

public class DeveloperNotFoundException extends NotFoundException {
    public DeveloperNotFoundException(Long id) {
        super("Застройщик с ID " + id + " не найден");
    }
}
