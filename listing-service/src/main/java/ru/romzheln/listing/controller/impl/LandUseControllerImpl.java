package ru.romzheln.listing.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.romzheln.listing.controller.LandUseController;
import ru.romzheln.listing.dto.request.reference.LandUseRequest;
import ru.romzheln.listing.dto.response.LandUseResponse;
import ru.romzheln.listing.model.entity.common.LandUse;
import ru.romzheln.listing.service.CrudService;

@RestController
@RequiredArgsConstructor
public class LandUseControllerImpl implements LandUseController {

    private final CrudService<LandUse, LandUseRequest, LandUseResponse> service;

    @Override
    public LandUseResponse create(LandUseRequest request) {
        return service.create(request);
    }

    @Override
    public LandUseResponse update(Long id, LandUseRequest request) {
        return service.update(id, request);
    }

    @Override
    public LandUseResponse findById(Long id) {
        return service.findById(id);
    }

    @Override
    public Page<LandUseResponse> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }
}
