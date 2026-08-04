package ru.romzheln.listing.exception;

public class ListingAlreadyPublished extends RuntimeException {
    public ListingAlreadyPublished(Long id) {
        super("Объявление с ID " + id + " уже опубликовано");
    }
}
