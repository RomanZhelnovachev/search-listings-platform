package ru.romzheln.listing.exception.badRequest;

public class PublishingRemovedListingException extends BadRequestException {
    public PublishingRemovedListingException(Long id) {
        super("Невозможно опубликовать сообщение с ID " + id + " , так как оно удалено");
    }
}
