package ru.romzheln.listing.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.romzheln.listing.dto.request.listing.*;
import ru.romzheln.listing.dto.response.ListingResponse;

@RequestMapping("/api/v1/listings")
public interface ListingController {

    @PostMapping
    ListingResponse create(@Valid @RequestBody CreateListingRequest request);

    @PutMapping("/{id}")
    ListingResponse update(@PathVariable Long id, @RequestBody
                           UpdateListingRequest request);

    @PatchMapping("/{id}/price")
    ListingResponse changePrice(@PathVariable Long id, @Valid @RequestBody
                                ChangePriceRequest request);

    @GetMapping("/{id}")
    ListingResponse getListing(@PathVariable Long id);

    @GetMapping
    Page<ListingResponse> getAll(Pageable pageable);

    @PostMapping("/{id}/remove")
    void remove(@PathVariable Long id, @RequestBody RemoveListingRequest request);

    @PatchMapping("/{id}/promotion")
    void addPromotion(@PathVariable Long id, @Valid @RequestBody
    ChangeListingPromotionRequest request);

    @DeleteMapping("/{id}/promotion")
    void disablePromotion(@PathVariable Long id);

    @PatchMapping("/{id}/mortgage")
    void addMortgagePrograms(@PathVariable Long id, @RequestBody
                             ChangeListingMortgageProgramsRequest request);

    @PatchMapping("/{id}/mortgage/remove")
    void removeMortgagePrograms(@PathVariable Long id, @RequestBody ChangeListingMortgageProgramsRequest request);

    @PostMapping("/{id}/publish")
    void publish(@PathVariable Long id);

    @PostMapping("/{id}/archive")
    void archive(@PathVariable Long id);

    @PostMapping("/{id}/approve")
    void approve(@PathVariable Long id);

    @PatchMapping("/{id}/images")
    void addImages(@PathVariable Long id, @RequestBody ChangeListingImageRequest request);

    @DeleteMapping("/{id}/images")
    void removeImages(@PathVariable Long id, @RequestBody ChangeListingImageRequest request);
}
