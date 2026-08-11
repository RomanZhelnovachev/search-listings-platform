package ru.romzheln.listing.exception.notFound;

public class PropertyNotFoundByIdException extends NotFoundException {
    public PropertyNotFoundByIdException(Long id) {
        super("Объект недвижимости с ID " + id + " не найден");
    }
}
