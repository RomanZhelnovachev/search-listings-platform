package ru.romzheln.listing.exception.badRequest;

public class ChangePropertyException extends BadRequestException {
    public ChangePropertyException(Long id) {
        super("У объекта недвижимости с ID " + id + " нет отличающегося поля для изменения");
    }
}
