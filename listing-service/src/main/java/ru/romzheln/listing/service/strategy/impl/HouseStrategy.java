package ru.romzheln.listing.service.strategy.impl;

import org.springframework.stereotype.Component;
import ru.romzheln.listing.dto.request.property.common.CreatePropertyRequest;
import ru.romzheln.listing.dto.request.property.common.UpdatePropertyRequest;
import ru.romzheln.listing.dto.request.property.house.CreateHouseRequest;
import ru.romzheln.listing.dto.request.property.house.UpdateHouseRequest;
import ru.romzheln.listing.mapper.PhysicalDetailsMapper;
import ru.romzheln.listing.model.entity.common.CommonLandDetails;
import ru.romzheln.listing.model.entity.common.CommonPhysicalDetails;
import ru.romzheln.listing.model.entity.common.LandUse;
import ru.romzheln.listing.model.entity.house.House;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.model.enums.PropertyType;
import ru.romzheln.listing.repository.PropertyRepository;
import ru.romzheln.listing.resolver.PropertyReferenceResolver;
import ru.romzheln.listing.service.impl.CommunicationServiceImpl;
import ru.romzheln.listing.service.strategy.AbstractPropertyStrategy;
import ru.romzheln.listing.util.ClassCastUtil;
import ru.romzheln.listing.util.UpdateUtil;

@Component
public class HouseStrategy extends AbstractPropertyStrategy {

    private final PropertyReferenceResolver resolver;
    private final PhysicalDetailsMapper mapper;

    protected HouseStrategy(CommunicationServiceImpl communicationService,
                            PropertyRepository propertyRepository,
                            PropertyReferenceResolver resolver,
                            PhysicalDetailsMapper mapper) {
        super(communicationService,
                propertyRepository);
        this.resolver = resolver;
        this.mapper = mapper;
    }

    @Override
    public Property create(CreatePropertyRequest request) {
        Property property = buildProperty(request);
        CreateHouseRequest houseRequest = ClassCastUtil.requireType(request, CreateHouseRequest.class);
        CommonPhysicalDetails commonPhysicalDetails = mapper.buildCommonPhysicalDetails(houseRequest.getCommonPhysicalDetailsDto());
        LandUse landUse = resolver.getLandUse(houseRequest.getCommonLandDetailsDto().landUse());
        CommonLandDetails commonLandDetails = mapper.buildCommonLandDetails(houseRequest.getCommonLandDetailsDto(), landUse);
        House house = House.builder()
                .property(property)
                .commonPhysicalDetails(commonPhysicalDetails)
                .commonLandDetails(commonLandDetails)
                .developer(resolver.getDeveloper(houseRequest.getDeveloperId()))
                .complex(resolver.getComplex(houseRequest.getComplexId()))
                .constructionStage(houseRequest.getConstructionStage())
                .additionalBuildings(resolver.getAllAdditionalBuildingsById(houseRequest.getAdditionalBuildings()))
                .landPlotSquare(houseRequest.getLandPlotSquare())
                .build();
        property.setHouse(house);
        return property;
    }

    @Override
    public Property update(Long id, UpdatePropertyRequest request) {
        Property property = findPropertyById(id, PropertyType.HOUSE);
        updateProperty(property, request);
        House house = property.getHouse();
        UpdateHouseRequest houseRequest = ClassCastUtil.requireType(request, UpdateHouseRequest.class);
        LandUse landUse = house.getCommonLandDetails().getLandUse();
        if (houseRequest.getCommonPhysicalDetailsDto() != null) {
            mapper.updateCommonPhysicalDetails(house.getCommonPhysicalDetails(), houseRequest.getCommonPhysicalDetailsDto());
        }
        if (houseRequest.getCommonLandDetailsDto() != null) {
            if (houseRequest.getCommonLandDetailsDto().landUse() != null) {
                landUse = resolver.getLandUse(houseRequest.getCommonLandDetailsDto().landUse());
            }
            mapper.updateCommonLandDetails(house.getCommonLandDetails(), houseRequest.getCommonLandDetailsDto(), landUse);
        }
        if (houseRequest.getDeveloperId() != null) {
            house.setDeveloper(resolver.getDeveloper(houseRequest.getDeveloperId()));
        }
        if (houseRequest.getComplexId() != null) {
            house.setComplex(resolver.getComplex(houseRequest.getComplexId()));
        }
        UpdateUtil.setIfNotNull(houseRequest.getConstructionStage(), house::setConstructionStage);
        if (houseRequest.getAdditionalBuildings() != null) {
            house.setAdditionalBuildings(resolver.getAllAdditionalBuildingsById(houseRequest.getAdditionalBuildings()));
        }
        UpdateUtil.setIfNotNull(houseRequest.getLandPlotSquare(), house::setLandPlotSquare);
        return property;
    }
}
