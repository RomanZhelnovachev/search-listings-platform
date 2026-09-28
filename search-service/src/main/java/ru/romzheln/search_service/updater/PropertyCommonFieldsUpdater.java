package ru.romzheln.search_service.updater;

import java.util.function.Consumer;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.common.*;
import ru.romzheln.search_service.dto.event.property.PropertyEvent;
import ru.romzheln.search_service.model.embeded.*;
import ru.romzheln.search_service.model.enums.*;

@Component
public class PropertyCommonFieldsUpdater {

  public void updateCommonFields(Property property, PropertyEvent event) {
    LocationDto locationDto = event.getLocation();
    if (locationDto != null) {
      updateLocation(property.getLocation(), locationDto);
    }
    updateIfNotNull(event.getSquare(), property::setSquare);
    updateIfNotNullEnum(event.getOwn(), Own.class, property::setOwn);
    updateIfNotNull(event.getFirstOwner(), property::setFirstOwner);
    if (event.getCommunicationIds() != null) {
      property.setCommunicationIds(event.getCommunicationIds());
    }
  }

  public void updateCommonPhysicalDetails(
      CommonPhysicalDetails details, CommonPhysicalDetailsDto dto) {
    updateIfNotNull(dto.roomsNumber(), details::setRoomsNumber);
    updateIfNotNull(dto.ceilingHeight(), details::setCeilingHeight);
    updateIfNotNullEnum(dto.renovation(), Renovation.class, details::setRenovation);
    updateIfNotNullEnum(dto.bathroom(), Bathroom.class, details::setBathroom);
    updateIfNotNullEnum(dto.material(), WallMaterial.class, details::setMaterial);
    updateIfNotNull(dto.completionDate(), details::setCompletionDate);
    updateIfNotNull(dto.yearBuilt(), details::setYearBuilt);
    updateIfNotNull(dto.floorsNumber(), details::setFloorsNumber);
    updateIfNotNullEnum(dto.view(), WindowView.class, details::setView);
    updateIfNotNullEnum(dto.balcony(), Balcony.class, details::setBalcony);
    updateIfNotNullEnum(dto.windowType(), WindowType.class, details::setWindowType);
    updateIfNotNullEnum(dto.windowMaterial(), WindowMaterial.class, details::setWindowMaterial);
    updateIfNotNullEnum(dto.layoutFeature(), LayoutFeature.class, details::setLayoutFeature);
    updateIfNotNullEnum(dto.layoutType(), LayoutType.class, details::setLayoutType);
  }

  public void updateApartmentPhysicalDetails(
      ApartmentPhysicalDetails details, ApartmentPhysicalDetailsDto dto) {
    updateIfNotNull(dto.kitchenSquare(), details::setKitchenSquare);
    updateIfNotNull(dto.floor(), details::setFloor);
    updateIfNotNullEnum(dto.elevator(), Elevator.class, details::setElevator);
    updateIfNotNullEnum(dto.ramp(), Ramp.class, details::setRamp);
    updateIfNotNullEnum(dto.side(), Side.class, details::setSide);
  }

  public void updateCommercialPhysicalDetails(
      CommercialPhysicalDetails details, CommercialPhysicalDetailsDto dto) {
    updateIfNotNull(dto.floor(), details::setFloor);
    updateIfNotNullEnum(dto.line(), Line.class, details::setLine);
    updateIfNotNullEnum(
        dto.propertyLocationType(), PropertyLocationType.class, details::setPropertyLocationType);
    updateIfNotNull(dto.separateEntrance(), details::setSeparateEntrance);
    updateIfNotNull(dto.ventilation(), details::setVentilation);
    updateIfNotNull(dto.tenantExists(), details::setTenantExists);
    updateIfNotNull(dto.entrancesNumber(), details::setEntrancesNumber);
    updateIfNotNull(dto.electricalPowerKw(), details::setElectricalPowerKw);
    updateIfNotNull(dto.railwayDeadEnd(), details::setRailwayDeadEnd);
  }

  public void updateCommonLandDetails(CommonLandDetails details, CommonLandDetailsDto dto) {
    updateIfNotNull(dto.landUse(), details::setLandUseId);
    updateIfNotNull(dto.landUseName(), details::setLandUseName);
    updateIfNotNull(dto.road(), details::setRoad);
    updateIfNotNull(dto.fencing(), details::setFencing);
  }

  public void updateDeveloper(Developer developer, Long id, String name) {
    updateIfNotNull(id, developer::setDeveloperId);
    updateIfNotNull(name, developer::setDeveloperName);
  }

  public void updateComplex(ResidentialComplex complex, Long id, String name) {
    updateIfNotNull(id, complex::setComplexId);
    updateIfNotNull(name, complex::setComplexName);
  }

  private void updateLocation(Location location, LocationDto dto) {
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

  private <T extends Enum<T>> void updateIfNotNullEnum(
      String value, Class<T> enumClass, Consumer<T> setter) {
    if (value != null) {
      T enumValue = Enum.valueOf(enumClass, value);
      setter.accept(enumValue);
    }
  }
}
