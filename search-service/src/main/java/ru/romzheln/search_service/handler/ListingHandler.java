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
    switch (message.eventType()) {
      case CREATED ->
          service.createReadModel(
              listingId,
              listingMapper.toListingCreatedEvent(message.listingPayload()),
              propertyMapper.toPropertyEvent(message.propertyPayload()));
      case UPDATED ->
          service.updateReadModel(
              listingId,
              listingMapper.toListingUpdatedEvent(message.listingPayload()),
              propertyMapper.toPropertyEvent(message.propertyPayload()));
      case UPDATED_PROPERTY ->
          service.updateProperty(
              listingId, propertyMapper.toPropertyEvent(message.propertyPayload()));
      case UPDATED_DEVELOPER ->
          service.updateDeveloper(
              listingId, propertyMapper.toDeveloperEvent(message.propertyPayload()));
      case UPDATED_LAND_USE ->
          service.updateLandUse(
              listingId, propertyMapper.toLandUseEvent(message.propertyPayload()));
      case UPDATED_COMPLEX ->
          service.updateResidentialComplex(
              listingId, propertyMapper.toResidentialComplexEvent(message.propertyPayload()));
      case PRICE_CHANGED ->
          service.changePrice(
              listingId, listingMapper.toChangePriceEvent(message.listingPayload()));
      case PROMOTION_ADDED ->
          service.addPromotion(listingId, listingMapper.toPromotionEvent(message.listingPayload()));
      case PROMOTION_DISABLED -> service.disablePromotion(listingId);
      case MORTGAGE_PROGRAM_ADDED ->
          service.addMortgageProgram(
              listingId, listingMapper.toMortgageProgramsEvent(message.listingPayload()));
      case MORTGAGE_PROGRAMS_REMOVED ->
          service.removeMortgageProgram(
              listingId, listingMapper.toMortgageProgramsEvent(message.listingPayload()));
        case PUBLISHED ->
    }
  }
}
