package ru.romzheln.search_service.service;

import ru.romzheln.search_service.dto.event.DeveloperEvent;
import ru.romzheln.search_service.dto.event.LandUseEvent;
import ru.romzheln.search_service.dto.event.ResidentialComplexEvent;
import ru.romzheln.search_service.dto.event.listing.*;
import ru.romzheln.search_service.dto.event.property.PropertyEvent;
import ru.romzheln.search_service.model.enums.PropertyType;

import java.time.Instant;

public interface ListingReadModelService {

void createReadModel(Long listingId, String region, PropertyType type, ListingCreatedEvent listingEvent, PropertyEvent propertyEvent, Instant time);

void updateReadModel(Long listingId, String region, PropertyType type, ListingUpdatedEvent listingEvent, PropertyEvent propertyEvent, Instant time);

void updateProperty(Long listingId, String region, PropertyType type, PropertyEvent event, Instant time);

void updateDeveloper(Long listingId, String region, PropertyType type, DeveloperEvent event, Instant time);

void updateLandUse(Long listingId, String region, PropertyType type, LandUseEvent event, Instant time);

void updateResidentialComplex(Long listingId, String region, PropertyType type, ResidentialComplexEvent event, Instant time);

void changePrice(Long listingId, String region, PropertyType type, ChangePriceEvent event, Instant time);

void addPromotion(Long listingId, String region, PropertyType type, PromotionEvent event, Instant time);

void disablePromotion(Long listingId, String region, PropertyType type, Instant time);

void changeMortgageProgram(Long listingId, String region, PropertyType type, MortgageProgramsEvent event, Instant time);

void publish(Long listingId, String region, PropertyType type, Instant time);

void archive(Long listingId, String region, PropertyType type, Instant time);

void approve(Long listingId, String region, PropertyType type, Instant time);

void changeImages(Long listingId, String region, PropertyType type, ImageEvent event, Instant time);

void removeReadModel(Long listingId, String region, PropertyType type);
}
