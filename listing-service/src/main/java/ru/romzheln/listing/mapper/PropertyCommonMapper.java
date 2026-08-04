package ru.romzheln.listing.mapper;

import org.springframework.stereotype.Component;
import ru.romzheln.listing.dto.common.*;
import ru.romzheln.listing.model.entity.apartment.ApartmentPhysicalDetails;
import ru.romzheln.listing.model.entity.commercial.Commercial;
import ru.romzheln.listing.model.entity.commercial.CommercialPhysicalDetails;
import ru.romzheln.listing.model.entity.commercial.Purpose;
import ru.romzheln.listing.model.entity.common.AdditionalBuilding;
import ru.romzheln.listing.model.entity.common.CommonLandDetails;
import ru.romzheln.listing.model.entity.common.CommonPhysicalDetails;
import ru.romzheln.listing.model.entity.common.Communication;
import ru.romzheln.listing.model.entity.property.Location;
import ru.romzheln.listing.model.entity.property.Property;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PropertyCommonMapper {

    public LocationDto buildLocationDto(Location location) {
        return LocationDto.builder()
                .region(location.getRegion())
                .populatedArea(location.getPopulatedArea())
                .street(location.getStreet())
                .house(location.getHouse())
                .building(location.getBuilding())
                .apartment(location.getApartment())
                .build();
    }

    public CommonPhysicalDetailsDto buildCommonPhysicalDetailsDto(CommonPhysicalDetails details){
        return CommonPhysicalDetailsDto.builder()
                .roomsNumber(details.getRoomsNumber())
                .ceilingHeight(details.getCeilingHeight())
                .renovation(details.getRenovation())
                .bathroom(details.getBathroom())
                .material(details.getMaterial())
                .completionDate(details.getCompletionDate())
                .yearBuilt(details.getYearBuilt())
                .floorsNumber(details.getFloorsNumber())
                .view(details.getView())
                .balcony(details.getBalcony())
                .windowType(details.getWindowType())
                .windowMaterial(details.getWindowMaterial())
                .layoutFeature(details.getLayoutFeature())
                .layoutType(details.getLayoutType())
                .build();
    }

    public ApartmentPhysicalDetailsDto buildApartmentPhysicalDetailsDto(ApartmentPhysicalDetails details){
        return ApartmentPhysicalDetailsDto.builder()
                .kitchenSquare(details.getKitchenSquare())
                .floor(details.getFloor())
                .elevator(details.getElevator())
                .ramp(details.getRamp())
                .side(details.getSide())
                .build();
    }

    public CommercialPhysicalDetailsDto buildCommercialPhysicalDetailsDto(CommercialPhysicalDetails details){
        return CommercialPhysicalDetailsDto.builder()
                .floor(details.getFloor())
                .line(details.getLine())
                .propertyLocationType(details.getPropertyLocationType())
                .territorialZone(details.getTerritorialZone())
                .separateEntrance(details.getSeparateEntrance())
                .ventilation(details.getVentilation())
                .tenantExists(details.getTenantExists())
                .entrancesNumber(details.getEntrancesNumber())
                .electricalPowerKw(details.getElectricalPowerKw())
                .railwayDeadEnd(details.getRailwayDeadEnd())
                .build();
    }

    public CommonLandDetailsDto buildCommonLandDetailsDto(CommonLandDetails details){
        return CommonLandDetailsDto.builder()
                .landUse(details.getLandUse().getId())
                .road(details.getRoad())
                .fencing(details.getFencing())
                .build();
    }

    public Set<Long> getCommunicationIds(Property property) {
        return property.getCommunications() == null ?
                new HashSet<>() :
                property.getCommunications()
                        .stream()
                        .map(Communication::getId)
                        .collect(Collectors.toSet());
    }

    public Set<Long> getPurposeIds(Commercial commercial) {
        return commercial.getPurposes() == null ?
                new HashSet<>() :
                commercial.getPurposes()
                        .stream()
                        .map(Purpose::getId)
                        .collect(Collectors.toSet());
    }

    public Set<Long> getAdditionalBuildingsIds(Set<AdditionalBuilding> additionalBuildings){
        return additionalBuildings == null ? new HashSet<>() : additionalBuildings
                .stream()
                .map(AdditionalBuilding::getId)
                .collect(Collectors.toSet());
    }
}
