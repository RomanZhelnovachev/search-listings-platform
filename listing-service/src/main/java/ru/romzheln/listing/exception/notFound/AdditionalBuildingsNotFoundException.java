package ru.romzheln.listing.exception.notFound;

public class AdditionalBuildingsNotFoundException extends NotFoundException {
    public AdditionalBuildingsNotFoundException(Long id) {
        super("Дополнительная постройка с ID " + id + " не найдена");
    }
}
