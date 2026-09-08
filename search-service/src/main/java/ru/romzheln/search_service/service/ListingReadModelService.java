package ru.romzheln.search_service.service;

import ru.romzheln.search_service.dto.event.DeveloperEvent;
import ru.romzheln.search_service.dto.event.LandUseEvent;
import ru.romzheln.search_service.dto.event.ResidentialComplexEvent;
import ru.romzheln.search_service.dto.event.listing.*;
import ru.romzheln.search_service.dto.event.property.PropertyEvent;

public interface ListingReadModelService {

void createReadModel(Long listingId, ListingCreatedEvent listingEvent, PropertyEvent propertyEvent);

void updateReadModel(Long listingId, ListingUpdatedEvent listingEvent, PropertyEvent propertyEvent);

void updateProperty(Long listingId, PropertyEvent event);

void updateDeveloper(Long listingId, DeveloperEvent event);

void updateLandUse(Long listingId, LandUseEvent event);

void updateResidentialComplex(Long listingId, ResidentialComplexEvent event);

void changePrice(Long listingId, ChangePriceEvent event);

void addPromotion(Long listingId, PromotionEvent event);

void disablePromotion(Long listingId);

void addMortgageProgram(Long listingId, MortgageProgramsEvent event);

void removeMortgageProgram(Long listingId, MortgageProgramsEvent event);

void publish(Long listingId);
}
