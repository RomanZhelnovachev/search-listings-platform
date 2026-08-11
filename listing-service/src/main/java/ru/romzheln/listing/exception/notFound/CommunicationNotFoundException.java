package ru.romzheln.listing.exception.notFound;

public class CommunicationNotFoundException extends NotFoundException {
    public CommunicationNotFoundException(Long id) {
        super("Коммуникация с ID " + id + " не найдена");
    }
}
