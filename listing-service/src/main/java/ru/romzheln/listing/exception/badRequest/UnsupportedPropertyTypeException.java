package ru.romzheln.listing.exception.badRequest;

import ru.romzheln.listing.model.enums.PropertyType;

public class UnsupportedPropertyTypeException extends BadRequestException {
    public UnsupportedPropertyTypeException(PropertyType type) {
        super("Неподдерживаемый тип недвижимости - " + type);
    }
}
