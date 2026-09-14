package ru.romzheln.search_service.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.common.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class CommonDtoMapper {

    public CommonLandDetailsDto getCommonLandDetailsDto(JsonNode payload) {
        return CommonLandDetailsDto.builder()
                .landUse(payload.get("landUse").asLong())
                .landUseName(payload.get("landUseName").asText())
                .road(payload.get("road").asText())
                .fencing(payload.get("fencing").asText())
                .build();
    }

    public Set<Long> getSetLong(JsonNode payload) {
        Set<Long> ids = new HashSet<>();
        if(payload != null && payload.isArray()){
            for(JsonNode node : payload){
                ids.add(node.asLong());
            }
        }
        return ids;
    }

    public CommercialPhysicalDetailsDto getCommercialPhysicalDetailsDto(JsonNode payload) {
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

    public CommonPhysicalDetailsDto getCommonPhysicalDetailsDto(JsonNode payload){
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

    public LocationDto getLocationDto(JsonNode payload) {
        return LocationDto.builder()
                .region(payload.get("region").asText())
                .populatedArea(payload.get("populatedArea").asText())
                .street(payload.get("street").asText())
                .house(payload.get("house").asText())
                .building(payload.get("building").asText())
                .apartment(payload.get("apartment").asText())
                .build();
    }

    public ApartmentPhysicalDetailsDto getApartmentPhysicalDetailsDto(JsonNode payload){
        return ApartmentPhysicalDetailsDto.builder()
                .kitchenSquare(new BigDecimal(payload.get("kitchenSquare").asText()))
                .floor(payload.get("floor").asInt())
                .elevator(payload.get("elevator").asText())
                .ramp(payload.get("ramp").asText())
                .side(payload.get("side").asText())
                .build();
    }
}
