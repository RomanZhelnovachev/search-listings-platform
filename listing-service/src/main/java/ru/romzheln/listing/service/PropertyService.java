package ru.romzheln.listing.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.romzheln.listing.dto.request.property.common.CreatePropertyRequest;
import ru.romzheln.listing.dto.request.property.common.UpdatePropertyRequest;
import ru.romzheln.listing.dto.response.PropertyResponse;
import ru.romzheln.listing.model.entity.property.Property;

public interface PropertyService {

    PropertyResponse createProperty(CreatePropertyRequest request);

    PropertyResponse updateProperty(Long id, UpdatePropertyRequest request);

    PropertyResponse findById(Long id);

    Page<PropertyResponse> getAll(Pageable pageable);

    Property getProperty(Long id);
}
