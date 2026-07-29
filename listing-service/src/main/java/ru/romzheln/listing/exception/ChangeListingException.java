package ru.romzheln.listing.exception;

public class ChangeListingException extends RuntimeException {
    public ChangeListingException(Long id) {
        super("Невозможно изменить объявление с ID " + id);
    }
}
