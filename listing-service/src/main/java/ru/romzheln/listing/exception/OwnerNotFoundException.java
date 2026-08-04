package ru.romzheln.listing.exception;

public class OwnerNotFoundException extends RuntimeException {
    public OwnerNotFoundException(Long ownerId) {
        super("В базе нет пользователя с ID " + ownerId);
    }
}
