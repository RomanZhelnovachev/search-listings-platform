package ru.romzheln.listing.exception.serverError;

public class InvalidCastException extends InternalServerException {
    public InvalidCastException(String expected, String got) {
        super("Ожидалось " + expected + " , но получил " + got);
    }
}
