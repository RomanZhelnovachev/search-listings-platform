package ru.romzheln.search_service.service;

import ru.romzheln.search_service.dto.event.DeveloperEvent;
import ru.romzheln.search_service.dto.event.LandUseEvent;
import ru.romzheln.search_service.dto.event.ResidentialComplexEvent;
import ru.romzheln.search_service.dto.event.listing.*;
import ru.romzheln.search_service.dto.event.property.PropertyEvent;

import java.time.Instant;

public interface ListingReadModelService {

void createReadModel(Long listingId, String region, ListingCreatedEvent listingEvent, PropertyEvent propertyEvent, Instant time);

void updateReadModel(Long listingId, String region, ListingUpdatedEvent listingEvent, PropertyEvent propertyEvent, Instant time);

void updateProperty(Long listingId, String region, PropertyEvent event, Instant time);

void updateDeveloper(Long listingId, String region, DeveloperEvent event, Instant time);

void updateLandUse(Long listingId, String region, LandUseEvent event, Instant time);

void updateResidentialComplex(Long listingId, String region, ResidentialComplexEvent event, Instant time);

void changePrice(Long listingId, String region, ChangePriceEvent event, Instant time);

void addPromotion(Long listingId, String region, PromotionEvent event, Instant time);

void disablePromotion(Long listingId, String region, Instant time);

void addMortgageProgram(Long listingId, String region, MortgageProgramsEvent event, Instant time);

void removeMortgageProgram(Long listingId, String region, MortgageProgramsEvent event, Instant time);

void publish(Long listingId, String region, Instant time);

void archive(Long listingId, String region, Instant time);

void approve(Long listingId, String region, Instant time);

void addImage(Long listingId, String region, ImageEvent event, Instant time);

void removeImage(Long listingId, String region, ImageEvent event, Instant time);

void removeReadModel(Long listingId, String region, Instant time);
}
