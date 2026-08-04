package ru.romzheln.listing.exception;

public class ListingAlreadyRemoved extends RuntimeException {
    public ListingAlreadyRemoved(Long id) {
        super("Объявление с ID " + id + " уже удалено");
    }
}
