package ru.romzheln.listing.exception.notFound;

public class LandUseNotFoundException extends NotFoundException {
    public LandUseNotFoundException(Long id) {
        super("Назначения земли с ID " + id + " не существует");
    }
}
