package ru.romzheln.listing.exception.badRequest;

public class UpdateListingException extends BadRequestException {
    public UpdateListingException(Long id) {
        super("Нет полей для изменения в объявлении с ID " + id);
    }
}
