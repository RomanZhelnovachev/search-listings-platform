package ru.romzheln.listing.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romzheln.listing.dto.event.DeveloperEvent;
import ru.romzheln.listing.dto.event.LandUseEvent;
import ru.romzheln.listing.dto.event.ResidentialComplexEvent;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.model.enums.EventType;
import ru.romzheln.listing.repository.PropertyRepository;
import ru.romzheln.listing.service.ListingService;
import ru.romzheln.listing.service.PropertyReferenceUpdateService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PropertyReferenceUpdateServiceImpl implements PropertyReferenceUpdateService {

    private final PropertyRepository propertyRepository;
    private final ListingService listingService;

    @Override
    @Transactional
    public void updateDeveloper(Long developerId, DeveloperEvent event) {
        List<Property> properties = new ArrayList<>(propertyRepository.findByApartmentDeveloperId(developerId));
        properties.addAll(propertyRepository.findByHouseDeveloperId(developerId));
        if(properties.isEmpty()){
            log.warn("Объекты недвижимости с застройщиком с ID {} не найдены", developerId);
            return;
        }
        for(Property property : properties){
            listingService.updateProperty(EventType.UPDATED_DEVELOPER, property.getId(), event, null);
        }
    }

    @Override
    @Transactional
    public void updateLandUse(Long landUseId, LandUseEvent event) {
        List<Property> properties = new ArrayList<>(propertyRepository.findByHouseCommonLandDetailsLandUseId(landUseId));
        properties.addAll(propertyRepository.findByLandPlotCommonLandDetailsLandUseId(landUseId));
        if(properties.isEmpty()){
            log.warn("Объекты недвижимости с назначением земли с ID {} не найдены", landUseId);
            return;
        }
        for(Property property : properties){
            listingService.updateProperty(EventType.UPDATED_LAND_USE, property.getId(), event, null);
        }
    }

    @Override
    @Transactional
    public void updateResidentialComplex(Long complexId, ResidentialComplexEvent event) {
        List<Property> properties = new ArrayList<>(propertyRepository.findByApartmentComplexId(complexId));
        properties.addAll(propertyRepository.findByHouseComplexId(complexId));
        if(properties.isEmpty()){
            log.warn("Объекты недвижимости с жилым комплексом с ID {} не найдены", complexId);
            return;
        }
        for(Property property : properties){
            listingService.updateProperty(EventType.UPDATED_COMPLEX, property.getId(), event, null);
        }
    }
}
