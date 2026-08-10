package ru.romzheln.listing.exception;

public class AdditionalBuildingsNotFoundException extends RuntimeException {
    public AdditionalBuildingsNotFoundException(Long id) {
        super("Дополнительная постройка с ID " + id + " не найдена");
    }
}
