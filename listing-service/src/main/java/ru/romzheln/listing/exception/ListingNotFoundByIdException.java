package ru.romzheln.listing.exception;

public class ListingNotFoundByIdException extends RuntimeException {
    public ListingNotFoundByIdException(Long listingId) {
        super("Объявление с ID " + listingId + " не найдено");
    }
}
