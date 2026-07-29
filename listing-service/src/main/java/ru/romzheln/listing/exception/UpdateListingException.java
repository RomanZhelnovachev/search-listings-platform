package ru.romzheln.listing.exception;

public class UpdateListingException extends RuntimeException {
    public UpdateListingException(Long id) {
        super("Нет полей для изменения в объявлении с ID " + id);
    }
}
