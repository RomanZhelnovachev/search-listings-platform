package ru.romzheln.listing.service.strategy.impl;

import lombok.RequiredArgsConstructor;
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
import ru.romzheln.listing.resolver.PropertyReferenceResolver;
import ru.romzheln.listing.service.strategy.AbstractPropertyStrategy;
import ru.romzheln.listing.util.ClassCastUtil;
import ru.romzheln.listing.util.UpdateUtil;

@RequiredArgsConstructor
public class HouseStrategy extends AbstractPropertyStrategy {

    private final PropertyReferenceResolver resolver;
    private final PhysicalDetailsMapper mapper;

    @Override
    public Property create(CreatePropertyRequest request) {
        Property property = buildProperty(request);
        CreateHouseRequest houseRequest = ClassCastUtil.requireType(request, CreateHouseRequest.class);
        CommonPhysicalDetails commonPhysicalDetails = mapper.buildCommonPhysicalDetails(houseRequest.getCommonPhysicalDetailsRequest());
        LandUse landUse = resolver.getLandUse(houseRequest.getCommonLandDetailsRequest().landUse());
        CommonLandDetails commonLandDetails = mapper.buildCommonLandDetails(houseRequest.getCommonLandDetailsRequest(), landUse);
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
    public Property update(UpdatePropertyRequest request) {
        Property property = findPropertyById(request.getId(), PropertyType.HOUSE);
        updateProperty(property, request);
        House house = property.getHouse();
        UpdateHouseRequest houseRequest = ClassCastUtil.requireType(request, UpdateHouseRequest.class);
        LandUse landUse = house.getCommonLandDetails().getLandUse();
        if(houseRequest.getCommonPhysicalDetailsRequest() != null){
            mapper.updateCommonPhysicalDetails(house.getCommonPhysicalDetails(), houseRequest.getCommonPhysicalDetailsRequest());
        }
        if(houseRequest.getCommonLandDetailsRequest().landUse() != null){
          landUse = resolver.getLandUse(houseRequest.getCommonLandDetailsRequest().landUse());
        }
        if(houseRequest.getCommonLandDetailsRequest() != null){
            mapper.updateCommonLandDetails(house.getCommonLandDetails(), houseRequest.getCommonLandDetailsRequest(), landUse);
        }
        UpdateUtil.setIfNotNull(resolver.getDeveloper(houseRequest.getDeveloperId()), house::setDeveloper);
        UpdateUtil.setIfNotNull(resolver.getComplex(houseRequest.getComplexId()), house::setComplex);
        UpdateUtil.setIfNotNull(houseRequest.getConstructionStage(), house::setConstructionStage);
        UpdateUtil.setIfNotNull(resolver.getAllAdditionalBuildingsById(houseRequest.getAdditionalBuildings()), house::setAdditionalBuildings);
        UpdateUtil.setIfNotNull(houseRequest.getLandPlotSquare(), house::setLandPlotSquare);
        return property;
    }
}
