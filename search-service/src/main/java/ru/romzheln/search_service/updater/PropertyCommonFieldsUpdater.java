package ru.romzheln.search_service.updater;

import java.util.Set;
import java.util.function.Consumer;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.common.*;
import ru.romzheln.search_service.dto.event.property.PropertyEvent;
import ru.romzheln.search_service.model.embeded.*;

@Component
public class PropertyCommonFieldsUpdater {

  public void updateCommonFields(Property property, PropertyEvent event) {
    LocationDto locationDto = event.getLocation();
    if (locationDto != null) {
      updateLocation(property.getLocation(), locationDto);
    }
    updateIfNotNull(event.getSquare(), property::setSquare);
    updateIfNotNull(event.getOwn(), property::setOwn);
    updateIfNotNull(event.getFirstOwner(), property::setFirstOwner);
    if (event.getCommunicationIds() != null) {
      property.setCommunicationIds(
          addIds(property.getCommunicationIds(), event.getCommunicationIds()));
    }
  }

  public void updateCommonPhysicalDetails(
      CommonPhysicalDetails details, CommonPhysicalDetailsDto dto) {
    updateIfNotNull(dto.roomsNumber(), details::setRoomsNumber);
    updateIfNotNull(dto.ceilingHeight(), details::setCeilingHeight);
    updateIfNotNull(dto.renovation(), details::setRenovation);
    updateIfNotNull(dto.bathroom(), details::setBathroom);
    updateIfNotNull(dto.material(), details::setMaterial);
    updateIfNotNull(dto.completionDate(), details::setCompletionDate);
    updateIfNotNull(dto.yearBuilt(), details::setYearBuilt);
    updateIfNotNull(dto.floorsNumber(), details::setFloorsNumber);
    updateIfNotNull(dto.view(), details::setView);
    updateIfNotNull(dto.balcony(), details::setBalcony);
    updateIfNotNull(dto.windowType(), details::setWindowType);
    updateIfNotNull(dto.windowMaterial(), details::setWindowMaterial);
    updateIfNotNull(dto.layoutFeature(), details::setLayoutFeature);
    updateIfNotNull(dto.layoutType(), details::setLayoutType);
  }

  public void updateApartmentPhysicalDetails(
      ApartmentPhysicalDetails details, ApartmentPhysicalDetailsDto dto) {
      updateIfNotNull(dto.kitchenSquare(), details :: setKitchenSquare);
      updateIfNotNull(dto.floor(), details :: setFloor);
      updateIfNotNull(dto.elevator(), details :: setElevator);
      updateIfNotNull(dto.ramp(), details :: setRamp);
      updateIfNotNull(dto.side(), details :: setSide);
  }

  public void updateCommercialPhysicalDetails(CommercialPhysicalDetails details, CommercialPhysicalDetailsDto dto){
      updateIfNotNull(dto.floor(), details :: setFloor);
      updateIfNotNull(dto.line(), details :: setLine);
      updateIfNotNull(dto.propertyLocationType(), details :: setPropertyLocationType);
      updateIfNotNull(dto.territorialZone(), details :: setTerritorialZone);
      updateIfNotNull(dto.separateEntrance(), details :: setSeparateEntrance);
      updateIfNotNull(dto.ventilation(), details :: setVentilation);
      updateIfNotNull(dto.tenantExists(), details :: setTenantExists);
      updateIfNotNull(dto.entrancesNumber(), details :: setEntrancesNumber);
      updateIfNotNull(dto.electricalPowerKw(), details :: setElectricalPowerKw);
      updateIfNotNull(dto.railwayDeadEnd(), details :: setRailwayDeadEnd);
  }

  public void updateCommonLandDetails(CommonLandDetails details, CommonLandDetailsDto dto){
      updateIfNotNull(dto.landUse(), details :: setLandUseId);
      updateIfNotNull(dto.landUseName(), details :: setLandUseName);
      updateIfNotNull(dto.road(), details :: setRoad);
      updateIfNotNull(dto.fencing(), details :: setFencing);
  }

  public void updateDeveloper(Developer developer, Long id, String name){
      updateIfNotNull(id, developer :: setDeveloperId);
      updateIfNotNull(name, developer :: setDeveloperName);
  }

  public void updateComplex(ResidentialComplex complex, Long id, String name){
      updateIfNotNull(id, complex :: setComplexId);
      updateIfNotNull(name, complex :: setComplexName);
  }

    public Set<Long> addIds(Set<Long> oldIds, Set<Long> newIds) {
        oldIds.addAll(newIds);
        return oldIds;
    }

  private void updateLocation(Location location, LocationDto dto) {
    updateIfNotNull(dto.region(), location::setRegion);
    updateIfNotNull(dto.populatedArea(), location::setPopulatedArea);
    updateIfNotNull(dto.street(), location::setStreet);
    updateIfNotNull(dto.house(), location::setHouse);
    updateIfNotNull(dto.building(), location::setBuilding);
    updateIfNotNull(dto.apartment(), location::setApartment);
  }

  private <T> void updateIfNotNull(T value, Consumer<T> setter) {
    if (value != null) {
      setter.accept(value);
    }
  }
}
