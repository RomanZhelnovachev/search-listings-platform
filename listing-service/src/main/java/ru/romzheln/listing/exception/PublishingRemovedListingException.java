package ru.romzheln.listing.exception;

public class PublishingRemovedListingException extends RuntimeException {
    public PublishingRemovedListingException(Long id) {
        super("Невозможно опубликовать сообщение с ID " + id + " , так как оно удалено");
    }
}
