package ru.romzheln.listing.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.romzheln.listing.controller.CommunicationController;
import ru.romzheln.listing.dto.request.reference.CommunicationRequest;
import ru.romzheln.listing.dto.response.CommunicationResponse;
import ru.romzheln.listing.model.entity.common.Communication;
import ru.romzheln.listing.service.CrudService;

@RestController
@RequiredArgsConstructor
public class CommunicationControllerImpl implements CommunicationController {

    private final CrudService<Communication, CommunicationRequest, CommunicationResponse> service;


    @Override
    public CommunicationResponse create(CommunicationRequest request) {
        return service.create(request);
    }

    @Override
    public CommunicationResponse update(Long id, CommunicationRequest request) {
        return service.update(id, request);
    }

    @Override
    public CommunicationResponse findById(Long id) {
        return service.findById(id);
    }

    @Override
    public Page<CommunicationResponse> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }
}
