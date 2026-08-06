package ru.romzheln.listing.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.romzheln.listing.dto.request.property.common.CreatePropertyRequest;
import ru.romzheln.listing.dto.request.property.common.UpdatePropertyRequest;
import ru.romzheln.listing.dto.response.PropertyResponse;

@RequestMapping("/api/v1/properties")
public interface PropertyController {

    @PostMapping
    PropertyResponse create(@Valid @RequestBody CreatePropertyRequest request);

    @PutMapping("/{id}")
    PropertyResponse update(@PathVariable Long id, @Valid @RequestBody
    UpdatePropertyRequest request);

    @GetMapping("/{id}")
    PropertyResponse findProperty(@PathVariable Long id);

    @GetMapping
    Page<PropertyResponse> getAll(Pageable pageable);

}
