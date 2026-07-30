package ru.romzheln.listing.exception;

public class PromotionAlreadyDisabled extends RuntimeException {
    public PromotionAlreadyDisabled(Long id) {
        super("У объявления с ID " + id + " нет активной промоакции");
    }
}
