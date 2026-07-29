package ru.romzheln.listing.service;

import ru.romzheln.listing.dto.request.CreatePropertyRequest;
import ru.romzheln.listing.dto.request.UpdatePropertyRequest;
import ru.romzheln.listing.dto.response.PropertyResponse;
import ru.romzheln.listing.model.entity.property.Property;

public interface PropertyService {

    PropertyResponse createProperty(CreatePropertyRequest request);

    PropertyResponse updateProperty(UpdatePropertyRequest request);

    Property findPropertyById(Long id);

    void deleteProperty(Long id);
}
