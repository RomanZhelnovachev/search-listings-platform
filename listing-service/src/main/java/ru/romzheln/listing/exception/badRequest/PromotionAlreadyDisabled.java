package ru.romzheln.listing.exception.badRequest;

public class PromotionAlreadyDisabled extends BadRequestException {
    public PromotionAlreadyDisabled(Long id) {
        super("У объявления с ID " + id + " нет активной промоакции");
    }
}
