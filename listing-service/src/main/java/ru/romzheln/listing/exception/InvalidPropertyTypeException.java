package ru.romzheln.listing.exception;

import ru.romzheln.listing.model.enums.PropertyType;

public class InvalidPropertyTypeException extends RuntimeException {
    public InvalidPropertyTypeException(Long id, PropertyType type) {
        super("Объект недвижимости с ID " + id + " не является " + type);
    }
}
