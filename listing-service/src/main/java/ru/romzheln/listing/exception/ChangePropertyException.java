package ru.romzheln.listing.exception;

public class ChangePropertyException extends RuntimeException {
    public ChangePropertyException(Long id) {
        super("У объекта недвижимости с ID " + id + " нет отличающегося поля для изменения");
    }
}
