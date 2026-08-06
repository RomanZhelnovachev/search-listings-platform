package ru.romzheln.listing.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.romzheln.listing.dto.request.reference.DeveloperRequest;
import ru.romzheln.listing.dto.response.DeveloperResponse;

@RequestMapping("/api/v1/developers")
public interface DeveloperController {

    @PostMapping
    DeveloperResponse create(@Valid @RequestBody DeveloperRequest request);

    @PutMapping("/{id}")
    DeveloperResponse update(@PathVariable Long id, @Valid @RequestBody DeveloperRequest request);

    @GetMapping("/{id}")
    DeveloperResponse findById(@PathVariable Long id);

    @GetMapping
    Page<DeveloperResponse> getAll(Pageable pageable);
}
