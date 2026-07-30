package ru.romzheln.listing.exception;

import ru.romzheln.listing.model.enums.ListingStatus;

public class ListingAlreadyApproved extends RuntimeException {
    public ListingAlreadyApproved(Long id, ListingStatus status) {
        super("Объявление с ID " + id + " уже прошло модерацию, текущий статус - " + status);
    }
}
