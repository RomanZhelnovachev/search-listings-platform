package ru.romzheln.listing.exception.badRequest;

import ru.romzheln.listing.model.enums.PropertyType;

public class InvalidPropertyTypeException extends BadRequestException {
    public InvalidPropertyTypeException(Long id, PropertyType type) {
        super("Объект недвижимости с ID " + id + " не является " + type);
    }
}
