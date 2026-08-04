package ru.romzheln.listing.exception;

import ru.romzheln.listing.model.enums.PropertyType;

public class PropertyStrategyNotFoundException extends RuntimeException {
    public PropertyStrategyNotFoundException(PropertyType type) {
        super(type + " - неподдерживаемый тип объекта недвижимости");
    }
}
