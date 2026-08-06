package ru.romzheln.listing.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.romzheln.listing.dto.request.reference.ResidentialComplexRequest;
import ru.romzheln.listing.dto.response.ResidentialComplexResponse;

@RequestMapping("/api/v1/properties/complexes")
public interface ResidentialComplexController {

    @PostMapping
    ResidentialComplexResponse create(@Valid @RequestBody ResidentialComplexRequest request);

    @PutMapping("/{id}")
    ResidentialComplexResponse update(@PathVariable Long id, @Valid @RequestBody ResidentialComplexRequest request);

    @GetMapping("/{id}")
    ResidentialComplexResponse findById(@PathVariable Long id);

    @GetMapping
    Page<ResidentialComplexResponse> getAll(Pageable pageable);
}
