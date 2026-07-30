package ru.romzheln.listing.exception;

import ru.romzheln.listing.model.enums.ListingStatus;

public class ListingArchiveException extends RuntimeException {
    public ListingArchiveException(Long id, ListingStatus status) {
        super("Текущий статус объявления с ID " + id + " - " + status + ". Невозможно заархивировать");
    }
}
