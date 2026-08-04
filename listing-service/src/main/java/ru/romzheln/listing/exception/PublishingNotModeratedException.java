package ru.romzheln.listing.exception;

public class PublishingNotModeratedException extends RuntimeException {
    public PublishingNotModeratedException(Long id) {
        super("Невозможно опубликовать сообщение с ID " + id + " , так как оно не прошло модерацию");
    }
}
