package ru.romzheln.search_service.service;

import ru.romzheln.search_service.dto.event.DeveloperEvent;
import ru.romzheln.search_service.dto.event.LandUseEvent;
import ru.romzheln.search_service.dto.event.ResidentialComplexEvent;
import ru.romzheln.search_service.dto.event.listing.*;
import ru.romzheln.search_service.dto.event.property.PropertyEvent;

public interface ListingReadModelService {

void createReadModel(Long listingId, String region, ListingCreatedEvent listingEvent, PropertyEvent propertyEvent);

void updateReadModel(Long listingId, String region, ListingUpdatedEvent listingEvent, PropertyEvent propertyEvent);

void updateProperty(Long listingId, String region, PropertyEvent event);

void updateDeveloper(Long listingId, String region, DeveloperEvent event);

void updateLandUse(Long listingId, String region, LandUseEvent event);

void updateResidentialComplex(Long listingId, String region, ResidentialComplexEvent event);

void changePrice(Long listingId, String region, ChangePriceEvent event);

void addPromotion(Long listingId, String region, PromotionEvent event);

void disablePromotion(Long listingId, String region);

void addMortgageProgram(Long listingId, String region, MortgageProgramsEvent event);

void removeMortgageProgram(Long listingId, String region, MortgageProgramsEvent event);

void publish(Long listingId, String region);

void archive(Long listingId, String region);

void approve(Long listingId, String region);

void addImage(Long listingId, String region, ImageEvent event);

void removeImage(Long listingId, String region, ImageEvent event);

void removeReadModel(Long listingId, String region);
}
