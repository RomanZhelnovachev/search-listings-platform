package ru.romzheln.listing.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.romzheln.listing.controller.PropertyController;
import ru.romzheln.listing.dto.request.property.common.CreatePropertyRequest;
import ru.romzheln.listing.dto.request.property.common.UpdatePropertyRequest;
import ru.romzheln.listing.dto.response.PropertyResponse;
import ru.romzheln.listing.service.PropertyService;

@RestController
@RequiredArgsConstructor
public class PropertyControllerImpl implements PropertyController {

    private final PropertyService service;

    @Override
    public PropertyResponse create(CreatePropertyRequest request){
        return service.createProperty(request);
    }

    @Override
    public PropertyResponse update(Long id, @RequestBody
                                   UpdatePropertyRequest request){
        return service.updateProperty(id, request);
    }

    @Override
    public PropertyResponse findProperty(Long id) {
        return service.findById(id);
    }

    @Override
    public Page<PropertyResponse> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }
}
