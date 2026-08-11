package ru.romzheln.listing.exception.badRequest;

public class PublishingNotModeratedException extends BadRequestException {
    public PublishingNotModeratedException(Long id) {
        super("Невозможно опубликовать сообщение с ID " + id + " , так как оно не прошло модерацию");
    }
}
