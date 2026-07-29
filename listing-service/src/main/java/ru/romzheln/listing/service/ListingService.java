package ru.romzheln.listing.service;

import ru.romzheln.listing.dto.request.CreateListingRequest;
import ru.romzheln.listing.dto.request.UpdateListingRequest;
import ru.romzheln.listing.dto.response.ListingResponse;
import ru.romzheln.listing.model.entity.listing.MortgageProgram;

import java.math.BigDecimal;
import java.util.Set;

public interface ListingService {

    ListingResponse createListing(CreateListingRequest request);

    ListingResponse updateListing(UpdateListingRequest request);

    ListingResponse changePrice(Long id, BigDecimal price);

    void addPromotion(Long id, Long promotionId);

    void addMortgageProgram(Long id, Set<MortgageProgram> mortgagePrograms);

    void publishListing(Long id);

    void archiveListing(Long id);

    ListingResponse findListingById(Long id);

    void deleteListing(Long id);
}
