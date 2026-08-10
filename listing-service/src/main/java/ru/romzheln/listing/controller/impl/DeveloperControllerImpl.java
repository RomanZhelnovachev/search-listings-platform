package ru.romzheln.listing.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.romzheln.listing.controller.DeveloperController;
import ru.romzheln.listing.dto.request.reference.DeveloperRequest;
import ru.romzheln.listing.dto.response.DeveloperResponse;
import ru.romzheln.listing.model.entity.common.Developer;
import ru.romzheln.listing.service.CrudService;

@RestController
@RequiredArgsConstructor
public class DeveloperControllerImpl implements DeveloperController {

    private final CrudService<Developer, DeveloperRequest, DeveloperResponse> service;

    @Override
    public DeveloperResponse create(DeveloperRequest request) {
        return service.create(request);
    }

    @Override
    public DeveloperResponse update(Long id, DeveloperRequest request) {
        return service.update(id, request);
    }

    @Override
    public DeveloperResponse findById(Long id) {
        return service.findById(id);
    }

    @Override
    public Page<DeveloperResponse> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }
}
