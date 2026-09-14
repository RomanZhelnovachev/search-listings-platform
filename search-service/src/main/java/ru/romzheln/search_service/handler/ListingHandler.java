package ru.romzheln.search_service.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.Message;
import ru.romzheln.search_service.mapper.ListingMapper;
import ru.romzheln.search_service.mapper.PropertyMapper;
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
    switch (message.eventType()) {
      case CREATED ->
          service.createReadModel(
              listingId,
              region,
              listingMapper.toListingCreatedEvent(message.listingPayload()),
              propertyMapper.toPropertyEvent(message.propertyPayload()),time);
      case UPDATED ->
          service.updateReadModel(
              listingId,
              region,
              listingMapper.toListingUpdatedEvent(message.listingPayload()),
              propertyMapper.toPropertyEvent(message.propertyPayload()),time);
      case UPDATED_PROPERTY ->
          service.updateProperty(
              listingId, region, propertyMapper.toPropertyEvent(message.propertyPayload()), time);
      case UPDATED_DEVELOPER ->
          service.updateDeveloper(
              listingId, region, propertyMapper.toDeveloperEvent(message.propertyPayload()), time);
      case UPDATED_LAND_USE ->
          service.updateLandUse(
              listingId, region, propertyMapper.toLandUseEvent(message.propertyPayload()), time);
      case UPDATED_COMPLEX ->
          service.updateResidentialComplex(
              listingId,
              region,
              propertyMapper.toResidentialComplexEvent(message.propertyPayload()), time);
      case PRICE_CHANGED ->
          service.changePrice(
              listingId, region, listingMapper.toChangePriceEvent(message.listingPayload()), time);
      case PROMOTION_ADDED ->
          service.addPromotion(
              listingId, region, listingMapper.toPromotionEvent(message.listingPayload()), time);
      case PROMOTION_DISABLED -> service.disablePromotion(listingId, region, time);
      case MORTGAGE_PROGRAM_ADDED ->
          service.addMortgageProgram(
              listingId, region, listingMapper.toMortgageProgramsEvent(message.listingPayload()), time);
      case MORTGAGE_PROGRAMS_REMOVED ->
          service.removeMortgageProgram(
              listingId, region, listingMapper.toMortgageProgramsEvent(message.listingPayload()), time);
      case PUBLISHED -> service.publish(listingId, region, time);
      case ARCHIVED -> service.archive(listingId, region, time);
      case APPROVED -> service.approve(listingId, region, time);
      case IMAGES_ADDED ->
          service.addImage(listingId, region, listingMapper.toImageEvent(message.listingPayload()), time);
      case IMAGES_REMOVED ->
          service.removeImage(
              listingId, region, listingMapper.toImageEvent(message.listingPayload()), time);
      case REMOVED -> service.removeReadModel(listingId, region, time);
    }
  }
}
