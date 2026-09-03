package ru.romzheln.listing.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.romzheln.listing.dto.event.DeveloperEvent;
import ru.romzheln.listing.dto.event.LandUseEvent;
import ru.romzheln.listing.dto.event.ResidentialComplexEvent;
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
  
  void updateDeveloper(Long developerId, DeveloperEvent event);
  
  void updateLandUse(Long landUseId, LandUseEvent event);
  
  void updateResidentialComplex(Long complexId, ResidentialComplexEvent event);
}
