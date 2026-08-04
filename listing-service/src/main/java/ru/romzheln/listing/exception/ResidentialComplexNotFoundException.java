package ru.romzheln.listing.exception;

public class ResidentialComplexNotFoundException extends RuntimeException {
    public ResidentialComplexNotFoundException(Long id) {
        super("Жилой комплекс с ID " + id + " не найден");
    }
}
