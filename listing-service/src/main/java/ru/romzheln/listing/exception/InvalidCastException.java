package ru.romzheln.listing.exception;

public class InvalidCastException extends RuntimeException {
    public InvalidCastException(String expected, String got) {
        super("Ожидалось " + expected + " , но получил " + got);
    }
}
