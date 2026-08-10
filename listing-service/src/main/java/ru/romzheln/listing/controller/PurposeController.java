package ru.romzheln.listing.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.romzheln.listing.dto.request.reference.PurposeRequest;
import ru.romzheln.listing.dto.response.PurposeResponse;

@RequestMapping("/api/v1/properties/purposes")
public interface PurposeController {

    @PostMapping
    PurposeResponse create(@Valid @RequestBody PurposeRequest request);

    @PutMapping("/{id}")
    PurposeResponse update(@PathVariable Long id, @Valid @RequestBody PurposeRequest request);

    @GetMapping("/{id}")
    PurposeResponse findById(@PathVariable Long id);

    @GetMapping
    Page<PurposeResponse> getAll(Pageable pageable);
}
