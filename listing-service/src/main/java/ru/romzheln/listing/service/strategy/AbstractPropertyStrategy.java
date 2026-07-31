package ru.romzheln.listing.service.strategy;

import lombok.RequiredArgsConstructor;
import ru.romzheln.listing.dto.request.property.CreatePropertyRequest;
import ru.romzheln.listing.model.entity.common.Communication;
import ru.romzheln.listing.model.entity.property.Location;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.repository.CommunicationRepository;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractPropertyStrategy implements PropertyStrategy {

    protected CommunicationRepository communicationRepository;

    protected Property buildProperty(CreatePropertyRequest request){
        Location location = Location.builder()
                .region(request.getLocation().region())
                .street(request.getLocation().street())
                .house(request.getLocation().house())
                .building(request.getLocation().building())
                .apartment(request.getLocation().apartment())
                .build();
        Set<Communication> communications = new HashSet<>(communicationRepository.findAllById(request.getCommunicationIds()));
        return Property.builder()
                .location(location)
                .square(request.getSquare())
                .propertyType(request.getPropertyType())
                .own(request.getOwn())
                .firstOwner(request.getFirstOwner())
                .communications(communications)
                .build();
    }
}
