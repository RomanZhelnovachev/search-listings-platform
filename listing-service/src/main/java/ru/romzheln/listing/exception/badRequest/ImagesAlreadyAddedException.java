package ru.romzheln.listing.exception.badRequest;

public class ImagesAlreadyAddedException extends BadRequestException {
    public ImagesAlreadyAddedException(String message) {
        super(message);
    }
}
