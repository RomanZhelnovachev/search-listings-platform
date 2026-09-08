package ru.romzheln.search_service.factory;

import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.listing.ListingCreatedEvent;
import ru.romzheln.search_service.dto.event.property.ApartmentEvent;
import ru.romzheln.search_service.dto.event.property.PropertyEvent;
import ru.romzheln.search_service.exception.UnknownPropertyType;
import ru.romzheln.search_service.model.embeded.Listing;
import ru.romzheln.search_service.model.embeded.ListingKey;
import ru.romzheln.search_service.model.enums.PropertyType;
import ru.romzheln.search_service.model.read_model.ListingApartmentReadModel;
import ru.romzheln.search_service.model.read_model.ReadModel;

@Component
public class ReadModelFactory {

  ReadModel createReadModel(
      Long listingId,
      String region,
      PropertyType propertyType,
      ListingCreatedEvent listingEvent,
      PropertyEvent propertyEvent) {
    switch (propertyType) {
        case APARTMENT -> {
            return createApartmentReadModel(listingId, region, listingEvent, propertyEvent);
        }
        case HOUSE -> {
            return createHouseReadModel(listingId, region, listingEvent, propertyEvent);
        }
        case COMMERCIAL -> {
            return createCommercialReadModel(listingId, region, listingEvent, propertyEvent);
        }
        case LAND_PLOT -> {
            return createLandPlotReadModel(listingId, region, listingEvent, propertyEvent);
        }
    }
    throw new UnknownPropertyType(propertyType);
  }

  private ListingApartmentReadModel createApartmentReadModel(
      Long listingId, String region, ListingCreatedEvent listingEvent, PropertyEvent propertyEvent) {
      ApartmentEvent event = (ApartmentEvent) propertyEvent;
      ListingKey key = new ListingKey(listingId, region);
      return ListingApartmentReadModel.builder()
              .key(key)
              .
              .build();
  }

  private Listing getListing(ListingCreatedEvent event){
      return Listing.builder()
              .title(event.title())
              .description(event.description())
              .listingStatus("CREATED")
              .imageIds()
              .ownerId()
              .propertyId()
              .property()
              .dealType()
              .price()
              .mortgageProgramIds()
              .promotionId()
              .build();
  }
}
