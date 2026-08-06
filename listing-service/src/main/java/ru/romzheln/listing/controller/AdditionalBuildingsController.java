package ru.romzheln.listing.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.romzheln.listing.dto.request.reference.AdditionalBuildingRequest;
import ru.romzheln.listing.dto.response.AdditionalBuildingResponse;

@RequestMapping("/api/v1/properties/buildings")
public interface AdditionalBuildingsController {

    @PostMapping
    AdditionalBuildingResponse create(@Valid @RequestBody AdditionalBuildingRequest request);

    @PutMapping("/{id}")
    AdditionalBuildingResponse update(@PathVariable Long id, @Valid @RequestBody AdditionalBuildingRequest request);

    @GetMapping("/{id}")
    AdditionalBuildingResponse findById(@PathVariable Long id);

    @GetMapping
    Page<AdditionalBuildingResponse> getAll(Pageable pageable);
}
