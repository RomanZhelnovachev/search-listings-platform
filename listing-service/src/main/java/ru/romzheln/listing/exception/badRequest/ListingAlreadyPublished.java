package ru.romzheln.listing.exception.badRequest;

public class ListingAlreadyPublished extends BadRequestException {
    public ListingAlreadyPublished(Long id) {
        super("Объявление с ID " + id + " уже опубликовано");
    }
}
