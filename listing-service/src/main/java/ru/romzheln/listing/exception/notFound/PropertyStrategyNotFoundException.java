package ru.romzheln.listing.exception.notFound;

import ru.romzheln.listing.model.enums.PropertyType;

public class PropertyStrategyNotFoundException extends NotFoundException {
    public PropertyStrategyNotFoundException(PropertyType type) {
        super(type + " - неподдерживаемый тип объекта недвижимости");
    }
}
