package ru.romzheln.search_service.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.DeveloperEvent;
import ru.romzheln.search_service.dto.event.LandUseEvent;
import ru.romzheln.search_service.dto.event.ResidentialComplexEvent;
import ru.romzheln.search_service.dto.event.common.*;
import ru.romzheln.search_service.dto.event.property.*;
import ru.romzheln.search_service.exception.UnknownPropertyType;
import ru.romzheln.search_service.model.enums.PropertyType;
import ru.romzheln.search_service.util.MapperUtil;

@Component
@RequiredArgsConstructor
public class PropertyMapper {

    private final CommonDtoMapper commonDtoMapper;

    public PropertyEvent toPropertyEvent(JsonNode payload){
        PropertyType propertyType = PropertyType.valueOf(MapperUtil.text(payload, "propertyType"));
        switch (propertyType){
            case APARTMENT -> {
                return createApartmentEvent(payload);
            }
            case COMMERCIAL -> {
                return createCommercialEvent(payload);
            }
            case HOUSE -> {
                return createHouseEvent(payload);
            }
            case LAND_PLOT -> {
                return createLandPlotEvent(payload);
            }
        }
        throw  new UnknownPropertyType(propertyType);
    }

    public DeveloperEvent toDeveloperEvent(JsonNode payload){
        return new DeveloperEvent(MapperUtil.text(payload, "name"));
    }

    public LandUseEvent toLandUseEvent(JsonNode payload){
        return new LandUseEvent(MapperUtil.text(payload, "name"));
    }

    public ResidentialComplexEvent toResidentialComplexEvent(JsonNode payload){
        return new ResidentialComplexEvent(MapperUtil.text(payload, "name"));
    }

    private LandPlotEvent createLandPlotEvent(JsonNode payload) {
        LandPlotEvent event = LandPlotEvent.builder()
                .commonLandDetailsDto(commonDtoMapper.getCommonLandDetailsDto(payload.get("commonLandDetailsDto")))
                .additionalBuildings(commonDtoMapper.getSetLong(payload.get("additionalBuildings")))
                .build();
        fillGeneralFields(event, PropertyType.LAND_PLOT, payload);
        return event;
    }

    private HouseEvent createHouseEvent(JsonNode payload) {
        HouseEvent event = HouseEvent.builder()
                .commonPhysicalDetailsDto(commonDtoMapper.getCommonPhysicalDetailsDto(payload.get("commonPhysicalDetailsDto")))
                .commonLandDetailsDto(commonDtoMapper.getCommonLandDetailsDto(payload.get("commonLandDetailsDto")))
                .developerId(MapperUtil.toLong(payload, "developerId"))
                .developerName(MapperUtil.text(payload, "developerName"))
                .complexId(MapperUtil.toLong(payload, "complexId"))
                .complexName(MapperUtil.text(payload, "complexName"))
                .constructionStage(MapperUtil.text(payload, "constructionStage"))
                .additionalBuildings(commonDtoMapper.getSetLong(payload.get("additionalBuildings")))
                .landPlotSquare(MapperUtil.decimal(payload, "landPlotSquare"))
                .build();
        fillGeneralFields(event, PropertyType.HOUSE, payload);
        return event;
    }    

    private CommercialEvent createCommercialEvent(JsonNode payload) {
        CommercialEvent event = CommercialEvent.builder()
                .commonPhysicalDetailsDto(commonDtoMapper.getCommonPhysicalDetailsDto(payload.get("commonPhysicalDetailsDto")))
                .commercialPhysicalDetailsDto(commonDtoMapper.getCommercialPhysicalDetailsDto(payload.get("commercialPhysicalDetailsDto")))
                .purposesIds(commonDtoMapper.getSetLong(payload.get("purposesIds")))
                .build();
        fillGeneralFields(event, PropertyType.COMMERCIAL, payload);
        return event;
    }    

    private ApartmentEvent createApartmentEvent(JsonNode payload){
        ApartmentEvent event = ApartmentEvent.builder()
                .apartmentType(MapperUtil.text(payload, "apartmentType"))
                .commonPhysicalDetailsDto(commonDtoMapper.getCommonPhysicalDetailsDto(payload.get("commonPhysicalDetailsDto")))
                .apartmentPhysicalDetailsDto(commonDtoMapper.getApartmentPhysicalDetailsDto(payload.get("apartmentPhysicalDetailsDto")))
                .developerId(MapperUtil.toLong(payload, "developerId"))
                .developerName(MapperUtil.text(payload, "developerName"))
                .complexId(MapperUtil.toLong(payload, "complexId"))
                .complexName(MapperUtil.text(payload, "complexName"))
                .build();
        fillGeneralFields(event, PropertyType.APARTMENT, payload);
        return event;
    }

    private void fillGeneralFields(PropertyEvent event, PropertyType type, JsonNode payload){
       event.setPropertyType(type);
       event.setLocation(commonDtoMapper.getLocationDto(payload.get("location")));
       event.setSquare(MapperUtil.decimal(payload, "square"));
       event.setOwn(MapperUtil.text(payload, "own"));
       event.setFirstOwner(MapperUtil.bool(payload, "firstOwner"));
       event.setCommunicationIds(commonDtoMapper.getSetLong(payload.get("communicationIds")));
    }
    
}
