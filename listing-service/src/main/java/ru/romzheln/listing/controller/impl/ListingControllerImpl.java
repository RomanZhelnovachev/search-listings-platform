package ru.romzheln.listing.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.romzheln.listing.controller.ListingController;
import ru.romzheln.listing.dto.request.listing.*;
import ru.romzheln.listing.dto.response.ListingResponse;
import ru.romzheln.listing.service.ListingService;

@RestController
@RequiredArgsConstructor
public class ListingControllerImpl implements ListingController {

    private final ListingService service;


    @Override
    public ListingResponse create(CreateListingRequest request) {
        return service.createListing(request);
    }

    @Override
    public ListingResponse update(Long id, UpdateListingRequest request) {
        return service.updateListing(id, request);
    }

    @Override
    public ListingResponse changePrice(Long id, ChangePriceRequest request) {
        return service.changePrice(id, request);
    }

    @Override
    public ListingResponse getListing(Long id) {
        return service.findListingById(id);
    }

    @Override
    public Page<ListingResponse> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    @Override
    public void remove(Long id, RemoveListingRequest request) {
        service.deleteListing(id, request);
    }

    @Override
    public void addPromotion(Long id, ChangeListingPromotionRequest request) {
        service.assignPromotion(id, request);
    }

    @Override
    public void disablePromotion(Long id) {
        service.disablePromotion(id);
    }

    @Override
    public void addMortgagePrograms(Long id, ChangeListingMortgageProgramsRequest request) {
        service.addMortgagePrograms(id, request);
    }

    @Override
    public void removeMortgagePrograms(Long id, ChangeListingMortgageProgramsRequest request) {
        service.removeMortgagePrograms(id, request);
    }

    @Override
    public void publish(Long id) {
        service.publishListing(id);
    }

    @Override
    public void archive(Long id) {
        service.archiveListing(id);
    }

    @Override
    public void approve(Long id) {
        service.approveListing(id);
    }

    @Override
    public void addImages(Long id, ChangeListingImageRequest request) {
        service.addImages(id, request);
    }

    @Override
    public void removeImages(Long id, ChangeListingImageRequest request) {
        service.removeImages(id, request);
    }
}
