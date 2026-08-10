package ru.romzheln.listing.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.romzheln.listing.dto.request.reference.LandUseRequest;
import ru.romzheln.listing.dto.response.LandUseResponse;

@RequestMapping("/api/v1/land_uses")
public interface LandUseController {

    @PostMapping
    LandUseResponse create(@Valid @RequestBody LandUseRequest request);

    @PutMapping("/{id}")
    LandUseResponse update(@PathVariable Long id, @Valid @RequestBody LandUseRequest request);

    @GetMapping("/{id}")
    LandUseResponse findById(@PathVariable Long id);

    @GetMapping
    Page<LandUseResponse> getAll(Pageable pageable);
}
