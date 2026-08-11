package ru.romzheln.listing.exception.notFound;

public class ResidentialComplexNotFoundException extends NotFoundException {
    public ResidentialComplexNotFoundException(Long id) {
        super("Жилой комплекс с ID " + id + " не найден");
    }
}
