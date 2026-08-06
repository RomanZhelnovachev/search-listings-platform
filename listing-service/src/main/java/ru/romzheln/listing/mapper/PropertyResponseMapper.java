package ru.romzheln.listing.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import ru.romzheln.listing.dto.response.*;
import ru.romzheln.listing.exception.UnsupportedPropertyTypeException;
import ru.romzheln.listing.model.entity.apartment.Apartment;
import ru.romzheln.listing.model.entity.commercial.Commercial;
import ru.romzheln.listing.model.entity.house.House;
import ru.romzheln.listing.model.entity.landPlot.LandPlot;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.model.enums.PropertyType;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PropertyResponseMapper {

    private final PropertyCommonMapper mapper;

    public PropertyResponse toResponse(Property property) {
        PropertyType type = property.getPropertyType();
        switch (type) {
            case APARTMENT -> {
                return buildApartmentResponse(property);
            }
            case COMMERCIAL -> {
                return buildCommercialResponse(property);
            }
            case HOUSE -> {
                return buildHouseResponse(property);
            }
            case LAND_PLOT -> {
                return buildLandPlotResponse(property);
            }
        }
        throw new UnsupportedPropertyTypeException(type);
    }

    public Page<PropertyResponse> toPageResponse(Page<Property> properties){
        List<PropertyResponse> responses = properties.getContent()
                .stream()
                .map(this::toResponse)
                .toList();
        return new PageImpl<>(responses, properties.getPageable(), properties.getTotalElements());
    }

    private ApartmentResponse buildApartmentResponse(Property property){
        Apartment apartment = property.getApartment();
        ApartmentResponse response = ApartmentResponse.builder()
                .apartmentType(apartment.getApartmentType())
                .commonPhysicalDetailsDto(mapper.buildCommonPhysicalDetailsDto(apartment.getCommonPhysicalDetails()))
                .apartmentPhysicalDetailsDto(mapper.buildApartmentPhysicalDetailsDto(apartment.getApartmentPhysicalDetails()))
                .developerId(apartment.getDeveloper().getId())
                .complexId(apartment.getComplex().getId())
                .build();
        fillGeneralFields(response, property);
        return response;
    }

    private CommercialResponse buildCommercialResponse(Property property){
        Commercial commercial = property.getCommercial();
        CommercialResponse response = CommercialResponse.builder()
                .commonPhysicalDetailsDto(mapper.buildCommonPhysicalDetailsDto(commercial.getCommonPhysicalDetails()))
                .commercialPhysicalDetailsDto(mapper.buildCommercialPhysicalDetailsDto(commercial.getCommercialPhysicalDetails()))
                .purposesIds(mapper.getPurposeIds(commercial))
                .build();
        fillGeneralFields(response, property);
        return response;
    }

    private HouseResponse buildHouseResponse(Property property){
        House house = property.getHouse();
        HouseResponse response = HouseResponse.builder()
                .commonPhysicalDetailsDto(mapper.buildCommonPhysicalDetailsDto(house.getCommonPhysicalDetails()))
                .commonLandDetailsDto(mapper.buildCommonLandDetailsDto(house.getCommonLandDetails()))
                .developerId(house.getDeveloper().getId())
                .complexId(house.getComplex().getId())
                .constructionStage(house.getConstructionStage())
                .additionalBuildings(mapper.getAdditionalBuildingsIds(house.getAdditionalBuildings()))
                .landPlotSquare(house.getLandPlotSquare())
                .build();
        fillGeneralFields(response, property);
        return response;
    }

    private LandPlotResponse buildLandPlotResponse(Property property){
        LandPlot landPlot = property.getLandPlot();
        LandPlotResponse response = LandPlotResponse.builder()
                .commonLandDetailsDto(mapper.buildCommonLandDetailsDto(landPlot.getCommonLandDetails()))
                .additionalBuildings(mapper.getAdditionalBuildingsIds(landPlot.getAdditionalBuildings()))
                .build();
        fillGeneralFields(response, property);
        return response;
    }

    private void fillGeneralFields(PropertyResponse response, Property property) {
        response.setId(property.getId());
        response.setPropertyType(property.getPropertyType());
        response.setLocation(mapper.buildLocationDto(property.getLocation()));
        response.setSquare(property.getSquare());
        response.setOwn(property.getOwn());
        response.setFirstOwner(property.getFirstOwner());
        response.setCommunicationIds(mapper.getCommunicationIds(property));
    }
}
