package ru.romzheln.listing.service;

import ru.romzheln.listing.dto.request.listing.CreateListingRequest;
import ru.romzheln.listing.dto.request.listing.UpdateListingRequest;
import ru.romzheln.listing.dto.response.ListingResponse;
import ru.romzheln.listing.model.entity.listing.Image;
import ru.romzheln.listing.model.entity.listing.MortgageProgram;

import java.math.BigDecimal;
import java.util.Set;

public interface ListingService {

    ListingResponse createListing(CreateListingRequest request);

    ListingResponse updateListing(UpdateListingRequest request);

    ListingResponse changePrice(Long id, BigDecimal price);

    void assignPromotion(Long id, Long promotionId);

    void disablePromotion(Long id);

    void addMortgagePrograms(Long id, Set<MortgageProgram> mortgagePrograms);

    void removeMortgagePrograms(Long id, Set<MortgageProgram> mortgagePrograms);

    void publishListing(Long id);

    void archiveListing(Long id);

    void approveListing(Long id);

    void addImages(Long id, Set<Image> images);

    void removeImages(Long id, Set<Image> images);

    ListingResponse findListingById(Long id);

    void deleteListing(Long id, String reason);
}
