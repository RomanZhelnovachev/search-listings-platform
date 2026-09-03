package ru.romzheln.search_service.service;

import ru.romzheln.search_service.dto.event.ListingCreatedEvent;
import ru.romzheln.search_service.model.read_model.ReadModel;

public interface ListingReadModelService {

ReadModel createReadModel(ListingCreatedEvent event);

void addDeveloper(Long developerId, String name);
}
