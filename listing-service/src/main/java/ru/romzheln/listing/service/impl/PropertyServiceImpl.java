package ru.romzheln.listing.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.romzheln.listing.dto.request.CreatePropertyRequest;
import ru.romzheln.listing.dto.request.UpdatePropertyRequest;
import ru.romzheln.listing.dto.response.PropertyResponse;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.service.PropertyService;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    @Override
    public PropertyResponse createProperty(CreatePropertyRequest request) {
        return null;
    }

    @Override
    public PropertyResponse updateProperty(UpdatePropertyRequest request) {
        return null;
    }

    @Override
    public Property findPropertyById(Long id) {
        return null;
    }

    @Override
    public void deleteProperty(Long id) {

    }
}
