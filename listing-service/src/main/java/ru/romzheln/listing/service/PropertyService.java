package ru.romzheln.listing.service;

import ru.romzheln.listing.dto.response.PropertyResponse;
import ru.romzheln.listing.model.entity.property.Property;

public interface PropertyService {

    PropertyResponse createProperty();

    PropertyResponse updateProperty();

    Property findPropertyById(Long id);

    void deleteProperty(Long id);
}
