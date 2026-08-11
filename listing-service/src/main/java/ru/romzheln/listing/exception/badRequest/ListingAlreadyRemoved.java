package ru.romzheln.listing.exception.badRequest;

public class ListingAlreadyRemoved extends BadRequestException {
    public ListingAlreadyRemoved(Long id) {
        super("Объявление с ID " + id + " уже удалено");
    }
}
