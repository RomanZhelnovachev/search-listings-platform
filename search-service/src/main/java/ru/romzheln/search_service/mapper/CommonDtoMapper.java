package ru.romzheln.search_service.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.common.*;
import ru.romzheln.search_service.util.MapperUtil;

@Component
public class CommonDtoMapper {

    public CommonLandDetailsDto getCommonLandDetailsDto(JsonNode payload) {
        return CommonLandDetailsDto.builder()
                .landUse(MapperUtil.toLong(payload, "landUse"))
                .landUseName(MapperUtil.text(payload,"landUseName"))
                .road(MapperUtil.text(payload, "road"))
                .fencing(MapperUtil.text(payload, "fencing"))
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
                .floor(MapperUtil.integer(payload, "floor"))
                .line(MapperUtil.text(payload, "line"))
                .propertyLocationType(MapperUtil.text(payload, "propertyLocationType"))
                .territorialZone(MapperUtil.text(payload, "territorialZone"))
                .separateEntrance(MapperUtil.bool(payload, "separateEntrance"))
                .ventilation(MapperUtil.bool(payload, "ventilation"))
                .tenantExists(MapperUtil.bool(payload, "tenantExists"))
                .entrancesNumber(MapperUtil.integer(payload, "entrancesNumber"))
                .electricalPowerKw(MapperUtil.integer(payload, "electricalPowerKw"))
                .railwayDeadEnd(MapperUtil.bool(payload, "railwayDeadEnd"))
                .build();
    }

    public CommonPhysicalDetailsDto getCommonPhysicalDetailsDto(JsonNode payload) {
        return CommonPhysicalDetailsDto.builder()
                .roomsNumber(MapperUtil.integer(payload, "roomsNumber"))
                .ceilingHeight(MapperUtil.decimal(payload, "ceilingHeight"))
                .renovation(MapperUtil.text(payload, "renovation"))
                .bathroom(MapperUtil.text(payload, "bathroom"))
                .material(MapperUtil.text(payload, "material"))
                .completionDate(MapperUtil.date(payload, "completionDate"))
                .yearBuilt(MapperUtil.integer(payload, "yearBuilt"))
                .floorsNumber(MapperUtil.integer(payload, "floorsNumber"))
                .view(MapperUtil.text(payload, "view"))
                .balcony(MapperUtil.text(payload, "balcony"))
                .windowType(MapperUtil.text(payload, "windowType"))
                .windowMaterial(MapperUtil.text(payload, "windowMaterial"))
                .layoutFeature(MapperUtil.text(payload, "layoutFeature"))
                .layoutType(MapperUtil.text(payload, "layoutType"))
                .build();
    }

    public LocationDto getLocationDto(JsonNode payload) {
        return LocationDto.builder()
                .region(MapperUtil.text(payload, "region"))
                .populatedArea(MapperUtil.text(payload, "populatedArea"))
                .street(MapperUtil.text(payload, "street"))
                .house(MapperUtil.text(payload, "house"))
                .building(MapperUtil.text(payload, "building"))
                .apartment(MapperUtil.text(payload, "apartment"))
                .build();
    }

    public ApartmentPhysicalDetailsDto getApartmentPhysicalDetailsDto(JsonNode payload){
        return ApartmentPhysicalDetailsDto.builder()
                .kitchenSquare(MapperUtil.decimal(payload, "kitchenSquare"))
                .floor(MapperUtil.integer(payload, "floor"))
                .elevator(MapperUtil.text(payload, "elevator"))
                .ramp(MapperUtil.text(payload, "ramp"))
                .side(MapperUtil.text(payload, "side"))
                .build();
    }    
}
