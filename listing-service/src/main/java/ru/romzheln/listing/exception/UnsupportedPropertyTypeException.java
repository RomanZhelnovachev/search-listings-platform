package ru.romzheln.listing.exception;

import ru.romzheln.listing.model.enums.PropertyType;

public class UnsupportedPropertyTypeException extends RuntimeException {
    public UnsupportedPropertyTypeException(PropertyType type) {
        super("Неподдерживаемый тип недвижимости - " + type);
    }
}
