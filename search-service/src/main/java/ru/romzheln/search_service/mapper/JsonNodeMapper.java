package ru.romzheln.search_service.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.CommunicationEvent;
import ru.romzheln.search_service.dto.event.PurposeEvent;
import ru.romzheln.search_service.dto.event.common.*;
import ru.romzheln.search_service.dto.event.listing.ListingCreatedEvent;
import ru.romzheln.search_service.dto.event.property.*;
import ru.romzheln.search_service.exception.UnknownPropertyType;
import ru.romzheln.search_service.model.enums.PropertyType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class JsonNodeMapper {

    public CommunicationEvent toCommunicationEvent(JsonNode payload){
        String type = payload.get("type").asText();
        return new CommunicationEvent(type);
    }

    public PurposeEvent toPurposeEvent(JsonNode payload){
        String name = payload.get("name").asText();
        return new PurposeEvent(name);
    }

    public ListingCreatedEvent toListingCreatedEvent(JsonNode payload){
       return ListingCreatedEvent.builder()
               .title(payload.get("title").asText())
               .description(payload.get("description").asText())
               .ownerId(payload.get("ownerId").asLong())
               .propertyId(payload.get("propertyId").asLong())
               .propertyType(payload.get("propertyType").asText())
               .dealType(payload.get("dealType").asText())
               .price(new BigDecimal(payload.get("price").asText()))
               .build();
    }

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

    private LandPlotEvent createLandPlotEvent(JsonNode payload) {
        LandPlotEvent event = LandPlotEvent.builder()
                .commonLandDetailsDto(getCommonLandDetailsDto(payload.get("commonLandDetailsDto")))
                .additionalBuildings(getSetLong(payload.get("additionalBuildings")))
                .build();
        fillGeneralFields(event, PropertyType.LAND_PLOT, payload);
        return event;
    }

    private HouseEvent createHouseEvent(JsonNode payload) {
        HouseEvent event = HouseEvent.builder()
                .commonPhysicalDetailsDto(getCommonPhysicalDetailsDto(payload.get("commonPhysicalDetailsDto")))
                .commonLandDetailsDto(getCommonLandDetailsDto(payload.get("commonLandDetailsDto")))
                .developerId(payload.get("developerId").asLong())
                .developerName(payload.get("developerName").asText())
                .complexId(payload.get("complexId").asLong())
                .complexName(payload.get("complexName").asText())
                .constructionStage(payload.get("constructionStage").asText())
                .additionalBuildings(getSetLong(payload.get("additionalBuildings")))
                .landPlotSquare(new BigDecimal(payload.get("landPlotSquare").asText()))
                .build();
        fillGeneralFields(event, PropertyType.HOUSE, payload);
        return event;
    }

    private CommonLandDetailsDto getCommonLandDetailsDto(JsonNode payload) {
        return CommonLandDetailsDto.builder()
                .landUse(payload.get("landUse").asLong())
                .landUseName(payload.get("landUseName").asText())
                .road(payload.get("road").asText())
                .fencing(payload.get("fencing").asText())
                .build();
    }

    private CommercialEvent createCommercialEvent(JsonNode payload) {
        CommercialEvent event = CommercialEvent.builder()
                .commonPhysicalDetailsDto(getCommonPhysicalDetailsDto(payload.get("commonPhysicalDetailsDto")))
                .commercialPhysicalDetailsDto(getCommercialPhysicalDetailsDto(payload.get("commercialPhysicalDetailsDto")))
                .purposesIds(getSetLong(payload.get("purposesIds")))
                .build();
        fillGeneralFields(event, PropertyType.COMMERCIAL, payload);
        return event;
    }

    private Set<Long> getSetLong(JsonNode payload) {
        Set<Long> ids = new HashSet<>();
        if(payload != null && payload.isArray()){
            for(JsonNode node : payload){
                ids.add(node.asLong());
            }
        }
        return ids;
    }

    private CommercialPhysicalDetailsDto getCommercialPhysicalDetailsDto(JsonNode payload) {
    return CommercialPhysicalDetailsDto.builder()
            .floor(payload.get("floor").asInt())
            .line(payload.get("line").asText())
            .propertyLocationType(payload.get("propertyLocationType").asText())
            .territorialZone(payload.get("territorialZone").asText())
            .separateEntrance(payload.get("separateEntrance").asBoolean())
            .ventilation(payload.get("ventilation").asBoolean())
            .tenantExists(payload.get("tenantExists").asBoolean())
            .entrancesNumber(payload.get("entrancesNumber").asInt())
            .electricalPowerKw(payload.get("electricalPowerKw").asInt())
            .railwayDeadEnd(payload.get("railwayDeadEnd").asBoolean())
            .build();
    }

    private ApartmentEvent createApartmentEvent(JsonNode payload){
        ApartmentEvent event = ApartmentEvent.builder()
                .apartmentType(payload.get("apartmentType").asText())
                .commonPhysicalDetailsDto(getCommonPhysicalDetailsDto(payload.get("commonPhysicalDetailsDto")))
                .apartmentPhysicalDetailsDto(getApartmentPhysicalDetailsDto(payload.get("apartmentPhysicalDetailsDto")))
                .developerId(payload.get("developerId").asLong())
                .developerName(payload.get("developerName").asText())
                .complexId(payload.get("complexId").asLong())
                .complexName(payload.get("complexName").asText())
                .build();
        fillGeneralFields(event, PropertyType.APARTMENT, payload);
        return event;
    }

    private CommonPhysicalDetailsDto getCommonPhysicalDetailsDto(JsonNode payload){
        return CommonPhysicalDetailsDto.builder()
                .roomsNumber(payload.get("roomsNumber").asInt())
                .ceilingHeight(new BigDecimal(payload.get("ceilingHeight").asText()))
                .renovation(payload.get("renovation").asText())
                .bathroom(payload.get("bathroom").asText())
                .material(payload.get("material").asText())
                .completionDate(LocalDate.parse(payload.get("completionDate").asText()))
                .yearBuilt(payload.get("yearBuilt").asInt())
                .floorsNumber(payload.get("floorsNumber").asInt())
                .view(payload.get("view").asText())
                .balcony(payload.get("balcony").asText())
                .windowType(payload.get("windowType").asText())
                .windowMaterial(payload.get("windowMaterial").asText())
                .layoutFeature(payload.get("layoutFeature").asText())
                .layoutType(payload.get("layoutType").asText())
                .build();
    }

    private ApartmentPhysicalDetailsDto getApartmentPhysicalDetailsDto(JsonNode payload){
        return ApartmentPhysicalDetailsDto.builder()
                .kitchenSquare(new BigDecimal(payload.get("kitchenSquare").asText()))
                .floor(payload.get("floor").asInt())
                .elevator(payload.get("elevator").asText())
                .ramp(payload.get("ramp").asText())
                .side(payload.get("side").asText())
                .build();
    }

    private void fillGeneralFields(PropertyEvent event, PropertyType type, JsonNode payload){
       event.setPropertyType(type);
       event.setLocation(getLocationDto(payload.get("location")));
       event.setSquare(new BigDecimal(payload.get("square").asText()));
       event.setOwn(payload.get("own").asText());
       event.setFirstOwner(payload.get("firstOwner").asBoolean());
       event.setCommunicationIds(getSetLong(payload.get("communicationIds")));
    }

    private LocationDto getLocationDto(JsonNode payload) {
        return LocationDto.builder()
                .region(payload.get("region").asText())
                .populatedArea(payload.get("populatedArea").asText())
                .street(payload.get("street").asText())
                .house(payload.get("house").asText())
                .building(payload.get("building").asText())
                .apartment(payload.get("apartment").asText())
                .build();
    }
}
