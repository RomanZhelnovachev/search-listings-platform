package ru.romzheln.listing.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.romzheln.listing.controller.AdditionalBuildingsController;
import ru.romzheln.listing.dto.request.reference.AdditionalBuildingRequest;
import ru.romzheln.listing.dto.response.AdditionalBuildingResponse;
import ru.romzheln.listing.model.entity.common.AdditionalBuilding;
import ru.romzheln.listing.service.CrudService;
import ru.romzheln.listing.service.impl.AdditionalBuildingsServiceImpl;

@RestController
@RequiredArgsConstructor
public class AdditionalBuildingsControllerImpl implements AdditionalBuildingsController {

    private final CrudService<AdditionalBuilding, AdditionalBuildingRequest, AdditionalBuildingResponse> service;

    @Override
    public AdditionalBuildingResponse create(AdditionalBuildingRequest request) {
        return service.create(request);
    }

    @Override
    public AdditionalBuildingResponse update(Long id, AdditionalBuildingRequest request) {
        return service.update(id, request);
    }

    @Override
    public AdditionalBuildingResponse findById(Long id) {
        return service.findById(id);
    }

    @Override
    public Page<AdditionalBuildingResponse> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }
}
