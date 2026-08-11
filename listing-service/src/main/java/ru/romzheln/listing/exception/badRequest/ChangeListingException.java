package ru.romzheln.listing.exception.badRequest;

public class ChangeListingException extends BadRequestException {
    public ChangeListingException(Long id) {
        super("Невозможно изменить объявление с ID " + id);
    }
}
