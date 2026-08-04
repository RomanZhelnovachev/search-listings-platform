package ru.romzheln.listing.service;

import ru.romzheln.listing.dto.request.property.common.CreatePropertyRequest;
import ru.romzheln.listing.dto.request.property.common.UpdatePropertyRequest;
import ru.romzheln.listing.dto.response.PropertyResponse;
import ru.romzheln.listing.model.entity.property.Property;

public interface PropertyService {

    PropertyResponse createProperty(CreatePropertyRequest request);

    PropertyResponse updateProperty(UpdatePropertyRequest request);

    Property findPropertyById(Long id);
}
