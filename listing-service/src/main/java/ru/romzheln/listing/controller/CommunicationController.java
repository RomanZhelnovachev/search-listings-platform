package ru.romzheln.listing.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.romzheln.listing.dto.request.reference.CommunicationRequest;
import ru.romzheln.listing.dto.response.CommunicationResponse;

@RequestMapping("/api/v1/properties/communication")
public interface CommunicationController {

    @PostMapping
    CommunicationResponse create(@Valid @RequestBody CommunicationRequest request);

    @PutMapping("/{id}")
    CommunicationResponse update(@PathVariable Long id, @Valid @RequestBody CommunicationRequest request);

    @GetMapping("/{id}")
    CommunicationResponse findById(@PathVariable Long id);

    @GetMapping
    Page<CommunicationResponse> getAll(Pageable pageable);
}
