package ru.romzheln.listing.service.strategy.impl;

import org.springframework.stereotype.Component;
import ru.romzheln.listing.dto.common.ApartmentPhysicalDetailsDto;
import ru.romzheln.listing.dto.request.property.apartment.CreateApartmentRequest;
import ru.romzheln.listing.dto.request.property.apartment.UpdateApartmentRequest;
import ru.romzheln.listing.dto.common.CommonPhysicalDetailsDto;
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
import ru.romzheln.listing.repository.CommunicationRepository;
import ru.romzheln.listing.repository.PropertyRepository;
import ru.romzheln.listing.resolver.PropertyReferenceResolver;
import ru.romzheln.listing.service.strategy.AbstractPropertyStrategy;
import ru.romzheln.listing.util.ClassCastUtil;

@Component
public class ApartmentStrategy extends AbstractPropertyStrategy {

    private final PropertyReferenceResolver resolver;
    private final PhysicalDetailsMapper mapper;

    protected ApartmentStrategy(CommunicationRepository communicationRepository,
                                PropertyRepository propertyRepository,
                                PropertyReferenceResolver resolver,
                                PhysicalDetailsMapper mapper) {
        super(communicationRepository,
                propertyRepository);
        this.resolver = resolver;
        this.mapper = mapper;
    }

    @Override
    public Property create(CreatePropertyRequest request) {
        Property property = buildProperty(request);
        CreateApartmentRequest apartmentRequest = ClassCastUtil.requireType(request, CreateApartmentRequest.class);
        CommonPhysicalDetailsDto commonPhysicalDetailsDto = apartmentRequest.getCommonPhysicalDetailsDto();
        ApartmentPhysicalDetailsDto apartmentPhysicalDetailsDto = apartmentRequest.getApartmentPhysicalDetailsDto();
        CommonPhysicalDetails commonPhysicalDetails = mapper.buildCommonPhysicalDetails(commonPhysicalDetailsDto);
        ApartmentPhysicalDetails apartmentPhysicalDetails = mapper.buildApartmentPhysicalDetails(apartmentPhysicalDetailsDto);
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
        if(apartmentRequest.getCommonPhysicalDetailsDto() != null){
            mapper.updateCommonPhysicalDetails(apartment.getCommonPhysicalDetails(), apartmentRequest.getCommonPhysicalDetailsDto());
        }
        if(apartmentRequest.getApartmentPhysicalDetailsDto() != null){
            mapper.updateApartmentPhysicalDetails(apartment.getApartmentPhysicalDetails(), apartmentRequest.getApartmentPhysicalDetailsDto());
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
