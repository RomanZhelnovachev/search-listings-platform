package ru.romzheln.listing.exception;

public class ImagesAlreadyAddedException extends RuntimeException {
    public ImagesAlreadyAddedException(String message) {
        super(message);
    }
}
