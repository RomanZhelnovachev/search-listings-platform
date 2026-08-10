package ru.romzheln.listing.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.romzheln.listing.controller.ResidentialComplexController;
import ru.romzheln.listing.dto.request.reference.ResidentialComplexRequest;
import ru.romzheln.listing.dto.response.ResidentialComplexResponse;
import ru.romzheln.listing.model.entity.common.ResidentialComplex;
import ru.romzheln.listing.service.CrudService;

@RestController
@RequiredArgsConstructor
public class ResidentialComplexControllerImpl implements ResidentialComplexController {

    private final CrudService<ResidentialComplex, ResidentialComplexRequest, ResidentialComplexResponse> service;

    @Override
    public ResidentialComplexResponse create(ResidentialComplexRequest request) {
        return service.create(request);
    }

    @Override
    public ResidentialComplexResponse update(Long id, ResidentialComplexRequest request) {
        return service.update(id, request);
    }

    @Override
    public ResidentialComplexResponse findById(Long id) {
        return service.findById(id);
    }

    @Override
    public Page<ResidentialComplexResponse> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }
}
