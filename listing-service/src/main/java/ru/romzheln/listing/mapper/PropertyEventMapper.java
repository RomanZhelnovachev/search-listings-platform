package ru.romzheln.listing.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.listing.dto.event.property.*;
import ru.romzheln.listing.exception.badRequest.UnsupportedPropertyTypeException;
import ru.romzheln.listing.model.entity.apartment.Apartment;
import ru.romzheln.listing.model.entity.commercial.Commercial;
import ru.romzheln.listing.model.entity.house.House;
import ru.romzheln.listing.model.entity.landPlot.LandPlot;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.model.enums.PropertyType;

@Component
@RequiredArgsConstructor
public class PropertyEventMapper {

    private final PropertyCommonMapper mapper;

    public PropertyEvent toPropertyEvent(Property property){
        PropertyType type = property.getPropertyType();
        switch (type){
            case APARTMENT -> {
                return buildApartmentEvent(property);
            }
            case COMMERCIAL -> {
                return buildCommercialEvent(property);
            }
            case HOUSE -> {
                return buildHouseEvent(property);
            }
            case LAND_PLOT -> {
                return buildLandPlotEvent(property);
            }
        }
        throw new UnsupportedPropertyTypeException(type);
    }

    private ApartmentEvent buildApartmentEvent(Property property){
        Apartment apartment = property.getApartment();
        ApartmentEvent event = ApartmentEvent.builder()
                .apartmentType(apartment.getApartmentType())
                .commonPhysicalDetailsDto(mapper.buildCommonPhysicalDetailsDto(apartment.getCommonPhysicalDetails()))
                .apartmentPhysicalDetailsDto(mapper.buildApartmentPhysicalDetailsDto(apartment.getApartmentPhysicalDetails()))
                .developerId(apartment.getDeveloper().getId())
                .complexId(apartment.getComplex().getId())
                .build();
        fillGeneralFields(event, property);
        return event;
    }

    private CommercialEvent buildCommercialEvent(Property property){
        Commercial commercial = property.getCommercial();
        CommercialEvent event = CommercialEvent.builder()
                .commonPhysicalDetailsDto(mapper.buildCommonPhysicalDetailsDto(commercial.getCommonPhysicalDetails()))
                .commercialPhysicalDetailsDto(mapper.buildCommercialPhysicalDetailsDto(commercial.getCommercialPhysicalDetails()))
                .purposesIds(mapper.getPurposeIds(commercial))
                .build();
        fillGeneralFields(event, property);
        return event;
    }

    private HouseEvent buildHouseEvent(Property property){
        House house = property.getHouse();
        HouseEvent event = HouseEvent.builder()
                .commonPhysicalDetailsDto(mapper.buildCommonPhysicalDetailsDto(house.getCommonPhysicalDetails()))
                .commonLandDetailsDto(mapper.buildCommonLandDetailsDto(house.getCommonLandDetails()))
                .developerId(house.getDeveloper().getId())
                .complexId(house.getComplex().getId())
                .constructionStage(house.getConstructionStage())
                .additionalBuildings(mapper.getAdditionalBuildingsIds(house.getAdditionalBuildings()))
                .landPlotSquare(house.getLandPlotSquare())
                .build();
        fillGeneralFields(event, property);
        return event;
    }

    private LandPlotEvent buildLandPlotEvent(Property property){
        LandPlot landPlot = property.getLandPlot();
        LandPlotEvent event = LandPlotEvent.builder()
                .commonLandDetailsDto(mapper.buildCommonLandDetailsDto(landPlot.getCommonLandDetails()))
                .additionalBuildings(mapper.getAdditionalBuildingsIds(landPlot.getAdditionalBuildings()))
                .build();
        fillGeneralFields(event, property);
        return event;
    }

    private void fillGeneralFields(PropertyEvent event, Property property){
        event.setPropertyType(property.getPropertyType());
        event.setLocation(mapper.buildLocationDto(property.getLocation()));
        event.setSquare(property.getSquare());
        event.setOwn(property.getOwn());
        event.setFirstOwner(property.getFirstOwner());
        event.setCommunicationIds(mapper.getCommunicationIds(property));
    }
}
