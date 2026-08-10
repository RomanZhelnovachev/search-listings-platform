package ru.romzheln.listing.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.romzheln.listing.dto.request.listing.*;
import ru.romzheln.listing.dto.response.ListingResponse;

public interface ListingService {

    ListingResponse createListing(CreateListingRequest request);

    ListingResponse updateListing(Long id, UpdateListingRequest request);

    ListingResponse changePrice(Long id, ChangePriceRequest request);

    void assignPromotion(Long id, ChangeListingPromotionRequest request);

    void disablePromotion(Long id);

    void addMortgagePrograms(Long id, ChangeListingMortgageProgramsRequest request);

    void removeMortgagePrograms(Long id, ChangeListingMortgageProgramsRequest request);

    void publishListing(Long id);

    void archiveListing(Long id);

    void approveListing(Long id);

    void addImages(Long id, ChangeListingImageRequest request);

    void removeImages(Long id, ChangeListingImageRequest request);

    ListingResponse findListingById(Long id);

    Page<ListingResponse> getAll(Pageable pageable);

    void deleteListing(Long id, RemoveListingRequest request);
}
