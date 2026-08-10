package ru.romzheln.listing.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.romzheln.listing.controller.PurposeController;
import ru.romzheln.listing.dto.request.reference.PurposeRequest;
import ru.romzheln.listing.dto.response.PurposeResponse;
import ru.romzheln.listing.model.entity.commercial.Purpose;
import ru.romzheln.listing.service.CrudService;

@RestController
@RequiredArgsConstructor
public class PurposeControllerImpl implements PurposeController {

    private final CrudService<Purpose, PurposeRequest, PurposeResponse> service;

    @Override
    public PurposeResponse create(PurposeRequest request) {
        return service.create(request);
    }

    @Override
    public PurposeResponse update(Long id, PurposeRequest request) {
        return service.update(id, request);
    }

    @Override
    public PurposeResponse findById(Long id) {
        return service.findById(id);
    }

    @Override
    public Page<PurposeResponse> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }
}
