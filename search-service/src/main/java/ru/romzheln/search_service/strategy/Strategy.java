package ru.romzheln.search_service.strategy;

import java.time.Instant;

import ru.romzheln.search_service.dto.event.DeveloperEvent;
import ru.romzheln.search_service.dto.event.LandUseEvent;
import ru.romzheln.search_service.dto.event.ResidentialComplexEvent;
import ru.romzheln.search_service.dto.event.property.PropertyEvent;
import ru.romzheln.search_service.model.embeded.ListingKey;
import ru.romzheln.search_service.model.read_model.ReadModel;

public interface Strategy <M extends ReadModel>{

    void save(ReadModel model);

    M getReadModel(ListingKey key);

    M getReadModelOrNull(ListingKey key);   

    boolean existsReadModel(ListingKey key);

    void updateProperty(ListingKey key, PropertyEvent event, Instant time);

    void updateRegion(ListingKey key, String newRegion);

    void updateComplex(ListingKey key, ResidentialComplexEvent event, Instant time);

    void updateLandUse(ListingKey key, LandUseEvent event, Instant time);

    void updateDeveloper(ListingKey key, DeveloperEvent event, Instant time);

    void removeReadModel(ListingKey key);
}
