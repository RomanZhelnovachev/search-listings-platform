package ru.romzheln.listing.service.strategy.impl;

import org.springframework.stereotype.Component;
import ru.romzheln.listing.dto.request.property.commercial.CreateCommercialRequest;
import ru.romzheln.listing.dto.request.property.commercial.UpdateCommercialRequest;
import ru.romzheln.listing.dto.request.property.common.CreatePropertyRequest;
import ru.romzheln.listing.dto.request.property.common.UpdatePropertyRequest;
import ru.romzheln.listing.mapper.PhysicalDetailsMapper;
import ru.romzheln.listing.model.entity.commercial.Commercial;
import ru.romzheln.listing.model.entity.commercial.CommercialPhysicalDetails;
import ru.romzheln.listing.model.entity.commercial.Purpose;
import ru.romzheln.listing.model.entity.common.CommonPhysicalDetails;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.model.enums.PropertyType;
import ru.romzheln.listing.repository.CommunicationRepository;
import ru.romzheln.listing.repository.PropertyRepository;
import ru.romzheln.listing.resolver.PropertyReferenceResolver;
import ru.romzheln.listing.service.strategy.AbstractPropertyStrategy;
import ru.romzheln.listing.util.ClassCastUtil;

import java.util.Set;

@Component
public class CommercialStrategy extends AbstractPropertyStrategy {

    private final PhysicalDetailsMapper mapper;
    private final PropertyReferenceResolver resolver;

    protected CommercialStrategy(CommunicationRepository communicationRepository,
                                 PropertyRepository propertyRepository,
                                 PhysicalDetailsMapper mapper,
                                 PropertyReferenceResolver resolver) {
        super(communicationRepository,
                propertyRepository);
        this.mapper = mapper;
        this.resolver = resolver;
    }

    @Override
    public Property create(CreatePropertyRequest request) {
        Property property = buildProperty(request);
        CreateCommercialRequest commercialRequest = ClassCastUtil.requireType(request, CreateCommercialRequest.class);
        CommonPhysicalDetails commonPhysicalDetails = mapper.buildCommonPhysicalDetails(commercialRequest.getCommonPhysicalDetailsDto());
        CommercialPhysicalDetails commercialPhysicalDetails = mapper.buildCommercialPhysicalDetails(commercialRequest.getCommercialPhysicalDetailsDto());
        Set<Purpose> purposes = resolver.getAllPurposesById(commercialRequest.getPurposesIds());
       Commercial commercial = Commercial.builder()
               .property(property)
               .commonPhysicalDetails(commonPhysicalDetails)
               .commercialPhysicalDetails(commercialPhysicalDetails)
               .purposes(purposes)
               .build();
        property.setCommercial(commercial);
        return property;
    }

    @Override
    public Property update(Long id, UpdatePropertyRequest request) {
        Property property = findPropertyById(id, PropertyType.COMMERCIAL);
        updateProperty(property, request);
        Commercial commercial = property.getCommercial();
        UpdateCommercialRequest commercialRequest = ClassCastUtil.requireType(request, UpdateCommercialRequest.class);
        if(commercialRequest.getCommonPhysicalDetailsDto() != null){
            mapper.updateCommonPhysicalDetails(commercial.getCommonPhysicalDetails(), commercialRequest.getCommonPhysicalDetailsDto());
        }
        if(commercialRequest.getCommercialPhysicalDetailsDto() != null){
            mapper.updateCommercialPhysicalDetails(commercial.getCommercialPhysicalDetails(), commercialRequest.getCommercialPhysicalDetailsDto());
        }
        if (commercialRequest.getPurposesIds() != null) {
            commercial.setPurposes(resolver.getAllPurposesById(commercialRequest.getPurposesIds()));
        }
        return property;
    }
}
