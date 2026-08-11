package ru.romzheln.listing.exception.notFound;

public class ListingNotFoundByIdException extends NotFoundException {
    public ListingNotFoundByIdException(Long listingId) {
        super("Объявление с ID " + listingId + " не найдено");
    }
}
