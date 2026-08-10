package ru.romzheln.listing.service.strategy;

import ru.romzheln.listing.dto.request.property.common.CreatePropertyRequest;
import ru.romzheln.listing.dto.common.LocationDto;
import ru.romzheln.listing.dto.request.property.common.UpdatePropertyRequest;
import ru.romzheln.listing.exception.InvalidPropertyTypeException;
import ru.romzheln.listing.exception.PropertyNotFoundByIdException;
import ru.romzheln.listing.model.entity.common.Communication;
import ru.romzheln.listing.model.entity.property.Location;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.model.enums.PropertyType;
import ru.romzheln.listing.repository.PropertyRepository;
import ru.romzheln.listing.service.impl.CommunicationServiceImpl;

import java.util.Set;

public abstract class AbstractPropertyStrategy implements PropertyStrategy {

    protected final CommunicationServiceImpl communicationService;
    protected final PropertyRepository propertyRepository;

    protected AbstractPropertyStrategy(CommunicationServiceImpl communicationService,
                                       PropertyRepository propertyRepository) {
        this.communicationService = communicationService;
        this.propertyRepository = propertyRepository;
    }

    protected Property buildProperty(CreatePropertyRequest request){
        Location location = buildLocation(request.getLocation());
        Set<Communication> communications = communicationService.getAllCommunicationsByIds(request.getCommunicationIds());
        return Property.builder()
                .location(location)
                .square(request.getSquare())
                .propertyType(request.getPropertyType())
                .own(request.getOwn())
                .firstOwner(request.getFirstOwner())
                .communications(communications)
                .build();
    }

    protected void updateProperty(Property property, UpdatePropertyRequest request){
        if(request.getLocationDto() != null){
            Location location = buildLocation(request.getLocationDto());
            property.setLocation(location);
        }
        if(request.getSquare() != null){
            property.setSquare(request.getSquare());
        }
        if(request.getOwn() != null){
            property.setOwn(request.getOwn());
        }
        if(request.getFirstOwner() != null){
            property.changeFirstOwner(request.getFirstOwner());
        }
        if(request.getCommunicationIds() != null){
            Set<Communication> communications = communicationService.getAllCommunicationsByIds(request.getCommunicationIds());
            property.setCommunications(communications);
        }
    }

    protected Property findPropertyById(Long id, PropertyType type){
        Property property = getById(id);
        if(property.getPropertyType() != type){
            throw new InvalidPropertyTypeException(id, type);
        }
        return property;
    }

    private Property getById(Long id){
        return propertyRepository.findById(id).orElseThrow(()-> new PropertyNotFoundByIdException(id));
    }

    private Location buildLocation(LocationDto request){
        return Location.builder()
                .region(request.region())
                .populatedArea(request.populatedArea())
                .street(request.street())
                .house(request.house())
                .building(request.building())
                .apartment(request.apartment())
                .build();
    }
}
