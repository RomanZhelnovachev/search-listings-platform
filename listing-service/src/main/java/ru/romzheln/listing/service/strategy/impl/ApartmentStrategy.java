package ru.romzheln.listing.service.strategy.impl;

import lombok.RequiredArgsConstructor;
import ru.romzheln.listing.dto.request.property.apartment.ApartmentPhysicalDetailsRequest;
import ru.romzheln.listing.dto.request.property.apartment.CreateApartmentRequest;
import ru.romzheln.listing.dto.request.property.apartment.UpdateApartmentRequest;
import ru.romzheln.listing.dto.request.property.common.CommonPhysicalDetailsRequest;
import ru.romzheln.listing.dto.request.property.common.CreatePropertyRequest;
import ru.romzheln.listing.dto.request.property.common.UpdatePropertyRequest;
import ru.romzheln.listing.model.entity.apartment.Apartment;
import ru.romzheln.listing.model.entity.apartment.ApartmentPhysicalDetails;
import ru.romzheln.listing.model.entity.common.CommonPhysicalDetails;
import ru.romzheln.listing.model.entity.common.Developer;
import ru.romzheln.listing.model.entity.common.ResidentialComplex;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.model.enums.PropertyType;
import ru.romzheln.listing.mapper.PhysicalDetailsMapper;
import ru.romzheln.listing.resolver.PropertyReferenceResolver;
import ru.romzheln.listing.service.strategy.AbstractPropertyStrategy;
import ru.romzheln.listing.util.ClassCastUtil;

@RequiredArgsConstructor
public class ApartmentStrategy extends AbstractPropertyStrategy {

    private final PropertyReferenceResolver resolver;
    private final PhysicalDetailsMapper mapper;

    @Override
    public Property create(CreatePropertyRequest request) {
        Property property = super.buildProperty(request);
        CreateApartmentRequest apartmentRequest = ClassCastUtil.requireType(request, CreateApartmentRequest.class);
        CommonPhysicalDetailsRequest commonPhysicalDetailsRequest = apartmentRequest.getCommonPhysicalDetailsRequest();
        ApartmentPhysicalDetailsRequest apartmentPhysicalDetailsRequest = apartmentRequest.getApartmentPhysicalDetailsRequest();
        CommonPhysicalDetails commonPhysicalDetails = mapper.buildCommonPhysicalDetails(commonPhysicalDetailsRequest);
        ApartmentPhysicalDetails apartmentPhysicalDetails = mapper.buildApartmentPhysicalDetails(apartmentPhysicalDetailsRequest);
        Apartment apartment = Apartment.builder()
                .property(property)
                .apartmentType(apartmentRequest.getApartmentType())
                .commonPhysicalDetails(commonPhysicalDetails)
                .apartmentPhysicalDetails(apartmentPhysicalDetails)
                .developer(resolver.getDeveloper(apartmentRequest.getDeveloperId()))
                .complex(resolver.getComplex(apartmentRequest.getComplexId()))
                .build();
        property.setApartment(apartment);
        return property;
    }

    @Override
    public Property update(UpdatePropertyRequest request) {
        Property property = findPropertyById(request.getId(), PropertyType.APARTMENT);
        updateProperty(property, request);
        Apartment apartment = property.getApartment();
        UpdateApartmentRequest apartmentRequest = ClassCastUtil.requireType(request, UpdateApartmentRequest.class);
        if(apartmentRequest.getCommonPhysicalDetailsRequest() != null){
            mapper.updateCommonPhysicalDetails(apartment.getCommonPhysicalDetails(), apartmentRequest.getCommonPhysicalDetailsRequest());
        }
        if(apartmentRequest.getApartmentPhysicalDetailsRequest() != null){
            mapper.updateApartmentPhysicalDetails(apartment.getApartmentPhysicalDetails(), apartmentRequest.getApartmentPhysicalDetailsRequest());
        }
        if(apartmentRequest.getDeveloperId() != null){
            Developer developer = resolver.getDeveloper(apartmentRequest.getDeveloperId());
            apartment.setDeveloper(developer);
        }
        if(apartmentRequest.getComplexId() != null){
            ResidentialComplex complex = resolver.getComplex(apartmentRequest.getComplexId());
            apartment.setComplex(complex);
        }
        return property;
    }
}
