package ru.romzheln.search_service.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import java.math.BigDecimal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.DeveloperEvent;
import ru.romzheln.search_service.dto.event.LandUseEvent;
import ru.romzheln.search_service.dto.event.ResidentialComplexEvent;
import ru.romzheln.search_service.dto.event.common.*;
import ru.romzheln.search_service.dto.event.property.*;
import ru.romzheln.search_service.exception.UnknownPropertyType;
import ru.romzheln.search_service.model.enums.PropertyType;

@Component
@RequiredArgsConstructor
public class PropertyMapper {

    private final CommonDtoMapper commonDtoMapper;

    public PropertyEvent toPropertyEvent(JsonNode payload){
        PropertyType propertyType = PropertyType.valueOf(payload.get("propertyType").asText());
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
        return new DeveloperEvent(payload.get("name").asText());
    }

    public LandUseEvent toLandUseEvent(JsonNode payload){
        return new LandUseEvent(payload.get("name").asText());
    }

    public ResidentialComplexEvent toResidentialComplexEvent(JsonNode payload){
        return new ResidentialComplexEvent(payload.get("name").asText());
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
                .developerId(payload.get("developerId").asLong())
                .developerName(payload.get("developerName").asText())
                .complexId(payload.get("complexId").asLong())
                .complexName(payload.get("complexName").asText())
                .constructionStage(payload.get("constructionStage").asText())
                .additionalBuildings(commonDtoMapper.getSetLong(payload.get("additionalBuildings")))
                .landPlotSquare(new BigDecimal(payload.get("landPlotSquare").asText()))
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
                .apartmentType(payload.get("apartmentType").asText())
                .commonPhysicalDetailsDto(commonDtoMapper.getCommonPhysicalDetailsDto(payload.get("commonPhysicalDetailsDto")))
                .apartmentPhysicalDetailsDto(commonDtoMapper.getApartmentPhysicalDetailsDto(payload.get("apartmentPhysicalDetailsDto")))
                .developerId(payload.get("developerId").asLong())
                .developerName(payload.get("developerName").asText())
                .complexId(payload.get("complexId").asLong())
                .complexName(payload.get("complexName").asText())
                .build();
        fillGeneralFields(event, PropertyType.APARTMENT, payload);
        return event;
    }

    private void fillGeneralFields(PropertyEvent event, PropertyType type, JsonNode payload){
       event.setPropertyType(type);
       event.setLocation(commonDtoMapper.getLocationDto(payload.get("location")));
       event.setSquare(new BigDecimal(payload.get("square").asText()));
       event.setOwn(payload.get("own").asText());
       event.setFirstOwner(payload.get("firstOwner").asBoolean());
       event.setCommunicationIds(commonDtoMapper.getSetLong(payload.get("communicationIds")));
    }
    
}
