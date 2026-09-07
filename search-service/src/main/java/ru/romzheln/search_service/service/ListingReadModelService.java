package ru.romzheln.search_service.service;

import ru.romzheln.search_service.dto.event.listing.ListingCreatedEvent;
import ru.romzheln.search_service.dto.event.property.PropertyEvent;
import ru.romzheln.search_service.model.read_model.ReadModel;

public interface ListingReadModelService {

ReadModel createReadModel(Long listingId, ListingCreatedEvent listingEvent, PropertyEvent propertyEvent);

void addDeveloper(Long developerId, String name);
}
