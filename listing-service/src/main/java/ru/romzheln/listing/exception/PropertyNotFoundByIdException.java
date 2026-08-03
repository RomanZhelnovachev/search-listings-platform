package ru.romzheln.listing.exception;

public class PropertyNotFoundByIdException extends RuntimeException {
    public PropertyNotFoundByIdException(Long id) {
        super("Объект недвижимости с ID " + id + " не найден");
    }
}
