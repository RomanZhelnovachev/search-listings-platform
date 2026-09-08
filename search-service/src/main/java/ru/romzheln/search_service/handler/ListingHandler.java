package ru.romzheln.search_service.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.Message;
import ru.romzheln.search_service.mapper.ListingMapper;
import ru.romzheln.search_service.mapper.PropertyMapper;
import ru.romzheln.search_service.service.ListingReadModelService;

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
    switch (message.eventType()) {
      case CREATED ->
          service.createReadModel(
              listingId,
              region,
              listingMapper.toListingCreatedEvent(message.listingPayload()),
              propertyMapper.toPropertyEvent(message.propertyPayload()));
      case UPDATED ->
          service.updateReadModel(
              listingId,
              region,
              listingMapper.toListingUpdatedEvent(message.listingPayload()),
              propertyMapper.toPropertyEvent(message.propertyPayload()));
      case UPDATED_PROPERTY ->
          service.updateProperty(
              listingId, region, propertyMapper.toPropertyEvent(message.propertyPayload()));
      case UPDATED_DEVELOPER ->
          service.updateDeveloper(
              listingId, region, propertyMapper.toDeveloperEvent(message.propertyPayload()));
      case UPDATED_LAND_USE ->
          service.updateLandUse(
              listingId, region, propertyMapper.toLandUseEvent(message.propertyPayload()));
      case UPDATED_COMPLEX ->
          service.updateResidentialComplex(
              listingId,
              region,
              propertyMapper.toResidentialComplexEvent(message.propertyPayload()));
      case PRICE_CHANGED ->
          service.changePrice(
              listingId, region, listingMapper.toChangePriceEvent(message.listingPayload()));
      case PROMOTION_ADDED ->
          service.addPromotion(
              listingId, region, listingMapper.toPromotionEvent(message.listingPayload()));
      case PROMOTION_DISABLED -> service.disablePromotion(listingId, region);
      case MORTGAGE_PROGRAM_ADDED ->
          service.addMortgageProgram(
              listingId, region, listingMapper.toMortgageProgramsEvent(message.listingPayload()));
      case MORTGAGE_PROGRAMS_REMOVED ->
          service.removeMortgageProgram(
              listingId, region, listingMapper.toMortgageProgramsEvent(message.listingPayload()));
      case PUBLISHED -> service.publish(listingId, region);
      case ARCHIVED -> service.archive(listingId, region);
      case APPROVED -> service.approve(listingId, region);
      case IMAGES_ADDED ->
          service.addImage(listingId, region, listingMapper.toImageEvent(message.listingPayload()));
      case IMAGES_REMOVED ->
          service.removeImage(
              listingId, region, listingMapper.toImageEvent(message.listingPayload()));
      case REMOVED -> service.removeReadModel(listingId, region);
    }
  }
}
