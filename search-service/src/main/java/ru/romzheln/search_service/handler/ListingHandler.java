package ru.romzheln.search_service.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.Message;
import ru.romzheln.search_service.mapper.ListingMapper;
import ru.romzheln.search_service.mapper.PropertyMapper;
import ru.romzheln.search_service.model.enums.PropertyType;
import ru.romzheln.search_service.service.ListingReadModelService;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ListingHandler implements Handler {

  private final ListingReadModelService service;
  private final ListingMapper listingMapper;
  private final PropertyMapper propertyMapper;

  @Override
  public void handle(Message message) {
    Long listingId = message.aggregateId();
    String region = message.region();
      Instant time = message.createdAt();
      PropertyType type = message.propertyType();
    switch (message.eventType()) {
      case CREATED ->
          service.createReadModel(
              listingId,
              region,
              type,
              listingMapper.toListingCreatedEvent(message.listingPayload()),
              propertyMapper.toPropertyEvent(message.propertyPayload()),time);
      case UPDATED ->
          service.updateReadModel(
              listingId,
              region,
                  type,
              listingMapper.toListingUpdatedEvent(message.listingPayload()),
              propertyMapper.toPropertyEvent(message.propertyPayload()),time);
      case UPDATED_PROPERTY ->
          service.updateProperty(
              listingId, region, type, propertyMapper.toPropertyEvent(message.propertyPayload()), time);
      case UPDATED_DEVELOPER ->
          service.updateDeveloper(
              listingId, region, type, propertyMapper.toDeveloperEvent(message.propertyPayload()), time);
      case UPDATED_LAND_USE ->
          service.updateLandUse(
              listingId, region, type, propertyMapper.toLandUseEvent(message.propertyPayload()), time);
      case UPDATED_COMPLEX ->
          service.updateResidentialComplex(
              listingId,
              region,
                  type,
              propertyMapper.toResidentialComplexEvent(message.propertyPayload()), time);
      case PRICE_CHANGED ->
          service.changePrice(
              listingId, region, type, listingMapper.toChangePriceEvent(message.listingPayload()), time);
      case PROMOTION_ADDED ->
          service.addPromotion(
              listingId, region, type, listingMapper.toPromotionEvent(message.listingPayload()), time);
      case PROMOTION_DISABLED -> service.disablePromotion(listingId, region, type, time);
      case MORTGAGE_PROGRAM_ADDED, MORTGAGE_PROGRAMS_REMOVED ->
          service.changeMortgageProgram(
              listingId, region, type, listingMapper.toMortgageProgramsEvent(message.listingPayload()), time);
        case PUBLISHED -> service.publish(listingId, region, type, time);
      case ARCHIVED -> service.archive(listingId, region, type, time);
      case APPROVED -> service.approve(listingId, region, type, time);
      case IMAGES_ADDED, IMAGES_REMOVED ->
          service.changeImages(listingId, region, type, listingMapper.toImageEvent(message.listingPayload()), time);
      case REMOVED -> service.removeReadModel(listingId, region, type);
    }
  }
}
