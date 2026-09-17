package ru.romzheln.search_service.factory;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.common.*;
import ru.romzheln.search_service.dto.event.listing.ListingCreatedEvent;
import ru.romzheln.search_service.dto.event.property.*;
import ru.romzheln.search_service.exception.UnknownPropertyType;
import ru.romzheln.search_service.model.embeded.*;
import ru.romzheln.search_service.model.enums.PropertyType;
import ru.romzheln.search_service.model.read_model.*;

@Component
@RequiredArgsConstructor
public class ReadModelFactory {

  private final EmbeddedFactory factory;

  public ReadModel createReadModel(
      Long listingId,
      String region,
      PropertyType propertyType,
      ListingCreatedEvent listingEvent,
      PropertyEvent propertyEvent,
      Instant time) {
    switch (propertyType) {
      case APARTMENT -> {
        return createApartmentReadModel(listingId, region, listingEvent, propertyEvent, time);
      }
      case HOUSE -> {
        return createHouseReadModel(listingId, region, listingEvent, propertyEvent, time);
      }
      case COMMERCIAL -> {
        return createCommercialReadModel(listingId, region, listingEvent, propertyEvent, time);
      }
      case LAND_PLOT -> {
        return createLandPlotReadModel(listingId, region, listingEvent, propertyEvent, time);
      }
    }
    throw new UnknownPropertyType(propertyType);
  }

  public ListingApartmentReadModel updateRegionApartment(
      String newRegion, ListingApartmentReadModel oldModel) {
    return ListingApartmentReadModel.builder()
        .key(factory.getListingKey(oldModel.getKey().getId(), newRegion))
        .listing(oldModel.getListing())
        .apartment(oldModel.getApartment())
        .isIncludedInSearch(oldModel.isIncludedInSearch())
        .createdAt(oldModel.getCreatedAt())
        .updatedAt(oldModel.getUpdatedAt())
        .build();
  }

  public ListingCommercialReadModel updateRegionCommercial(
      String newRegion, ListingCommercialReadModel oldModel) {
    return ListingCommercialReadModel.builder()
        .key(factory.getListingKey(oldModel.getKey().getId(), newRegion))
        .listing(oldModel.getListing())
        .commercial(oldModel.getCommercial())
        .isIncludedInSearch(oldModel.isIncludedInSearch())
        .createdAt(oldModel.getCreatedAt())
        .updatedAt(oldModel.getUpdatedAt())
        .build();
  }

  public ListingHouseReadModel updateRegionHouse(String newRegion, ListingHouseReadModel oldModel) {
    return ListingHouseReadModel.builder()
        .key(factory.getListingKey(oldModel.getKey().getId(), newRegion))
        .listing(oldModel.getListing())
        .house(oldModel.getHouse())
        .isIncludedInSearch(oldModel.isIncludedInSearch())
        .createdAt(oldModel.getCreatedAt())
        .updatedAt(oldModel.getUpdatedAt())
        .build();
  }

  public ListingLandPlotReadModel updateRegionLandPlot(
      String newRegion, ListingLandPlotReadModel oldModel) {
    return ListingLandPlotReadModel.builder()
        .key(factory.getListingKey(oldModel.getKey().getId(), newRegion))
        .listing(oldModel.getListing())
        .landPlot(oldModel.getLandPlot())
        .isIncludedInSearch(oldModel.isIncludedInSearch())
        .createdAt(oldModel.getCreatedAt())
        .updatedAt(oldModel.getUpdatedAt())
        .build();
  }

  private ListingLandPlotReadModel createLandPlotReadModel(
      Long listingId,
      String region,
      ListingCreatedEvent listingEvent,
      PropertyEvent propertyEvent,
      Instant time) {
    LandPlotEvent event = (LandPlotEvent) propertyEvent;
    return ListingLandPlotReadModel.builder()
        .key(factory.getListingKey(listingId, region))
        .listing(factory.getListing(listingEvent, propertyEvent))
        .landPlot(factory.getLandPlot(event))
        .isIncludedInSearch(false)
        .createdAt(time)
        .build();
  }

  private ListingCommercialReadModel createCommercialReadModel(
      Long listingId,
      String region,
      ListingCreatedEvent listingEvent,
      PropertyEvent propertyEvent,
      Instant time) {
    CommercialEvent event = (CommercialEvent) propertyEvent;
    return ListingCommercialReadModel.builder()
        .key(factory.getListingKey(listingId, region))
        .listing(factory.getListing(listingEvent, propertyEvent))
        .commercial(factory.getCommercial(event))
        .isIncludedInSearch(false)
        .createdAt(time)
        .build();
  }

  private ListingHouseReadModel createHouseReadModel(
      Long listingId,
      String region,
      ListingCreatedEvent listingEvent,
      PropertyEvent propertyEvent,
      Instant time) {
    HouseEvent event = (HouseEvent) propertyEvent;
    return ListingHouseReadModel.builder()
        .key(factory.getListingKey(listingId, region))
        .listing(factory.getListing(listingEvent, propertyEvent))
        .house(factory.getHouse(event))
        .isIncludedInSearch(false)
        .createdAt(time)
        .build();
  }

  private ListingApartmentReadModel createApartmentReadModel(
      Long listingId,
      String region,
      ListingCreatedEvent listingEvent,
      PropertyEvent propertyEvent,
      Instant time) {
    ApartmentEvent event = (ApartmentEvent) propertyEvent;
    return ListingApartmentReadModel.builder()
        .key(factory.getListingKey(listingId, region))
        .listing(factory.getListing(listingEvent, propertyEvent))
        .apartment(factory.getApartment(event))
        .isIncludedInSearch(false)
        .createdAt(time)
        .build();
  }
}
