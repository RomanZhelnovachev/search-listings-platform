package ru.romzheln.listing.exception.badRequest;

import ru.romzheln.listing.model.enums.ListingStatus;

public class ListingArchiveException extends BadRequestException {
    public ListingArchiveException(Long id, ListingStatus status) {
        super("Текущий статус объявления с ID " + id + " - " + status + ". Невозможно заархивировать");
    }
}
