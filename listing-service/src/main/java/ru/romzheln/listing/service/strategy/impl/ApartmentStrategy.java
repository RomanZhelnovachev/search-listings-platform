package ru.romzheln.listing.service.strategy.impl;

import lombok.RequiredArgsConstructor;
import ru.romzheln.listing.dto.request.property.*;
import ru.romzheln.listing.exception.DeveloperNotFoundException;
import ru.romzheln.listing.exception.ResidentialComplexNotFoundException;
import ru.romzheln.listing.model.entity.apartment.Apartment;
import ru.romzheln.listing.model.entity.apartment.ApartmentPhysicalDetails;
import ru.romzheln.listing.model.entity.common.CommonPhysicalDetails;
import ru.romzheln.listing.model.entity.common.Developer;
import ru.romzheln.listing.model.entity.common.ResidentialComplex;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.repository.DeveloperRepository;
import ru.romzheln.listing.repository.ResidentialComplexRepository;
import ru.romzheln.listing.service.factory.PhysicalDetailsFactory;
import ru.romzheln.listing.service.strategy.AbstractPropertyStrategy;

@RequiredArgsConstructor
public class ApartmentStrategy extends AbstractPropertyStrategy {

    private final DeveloperRepository developerRepository;
    private final ResidentialComplexRepository complexRepository;
    private final PhysicalDetailsFactory factory;

    @Override
    public Property create(CreatePropertyRequest request) {
        Property property = super.buildProperty(request);
        CreateApartmentRequest apartmentRequest = (CreateApartmentRequest) request;

        CommonPhysicalDetailsRequest commonPhysicalDetailsRequest = apartmentRequest.getCommonPhysicalDetailsRequest();

        ApartmentPhysicalDetailsRequest apartmentPhysicalDetailsRequest = apartmentRequest.getApartmentPhysicalDetailsRequest();

        CommonPhysicalDetails commonPhysicalDetails = factory.buildCommonPhysicalDetails(commonPhysicalDetailsRequest);

        ApartmentPhysicalDetails apartmentPhysicalDetails = factory.buildApartmentPhysicalDetails(apartmentPhysicalDetailsRequest);

        Apartment apartment = Apartment.builder()
                .property(property)
                .apartmentType(apartmentRequest.getApartmentType())
                .commonPhysicalDetails(commonPhysicalDetails)
                .apartmentPhysicalDetails(apartmentPhysicalDetails)
                .developer(getDeveloper(apartmentRequest.getDeveloperId()))
                .complex(getComplex(apartmentRequest.getComplexId()))
                .build();
        property.setApartment(apartment);
        return property;
    }

    @Override
    public Property update(UpdatePropertyRequest request) {
        return null;
    }

    private Developer getDeveloper(Long id){
        return developerRepository.findById(id).orElseThrow(()-> new DeveloperNotFoundException(id));
    }

    private ResidentialComplex getComplex(Long id){
        return complexRepository.findById(id).orElseThrow(()-> new ResidentialComplexNotFoundException(id));
    }
}
