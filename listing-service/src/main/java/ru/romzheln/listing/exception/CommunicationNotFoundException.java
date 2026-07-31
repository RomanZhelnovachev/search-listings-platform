package ru.romzheln.listing.exception;

public class CommunicationNotFoundException extends RuntimeException {
    public CommunicationNotFoundException(Long id) {
        super("Коммуникация с ID " + id + " не найдена");
    }
}
