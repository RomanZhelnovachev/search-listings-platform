package ru.romzheln.search_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romzheln.search_service.dto.event.DeveloperEvent;
import ru.romzheln.search_service.dto.event.LandUseEvent;
import ru.romzheln.search_service.dto.event.ResidentialComplexEvent;
import ru.romzheln.search_service.dto.event.listing.*;
import ru.romzheln.search_service.dto.event.property.PropertyEvent;
import ru.romzheln.search_service.exception.ReadModelAlreadyExistsException;
import ru.romzheln.search_service.factory.ReadModelFactory;
import ru.romzheln.search_service.model.embeded.ListingKey;
import ru.romzheln.search_service.model.enums.PropertyType;
import ru.romzheln.search_service.model.read_model.ReadModel;
import ru.romzheln.search_service.resolver.ReadModelResolver;
import ru.romzheln.search_service.service.ListingReadModelService;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListingReadModelServiceImpl implements ListingReadModelService {

    private final ReadModelResolver resolver;
    private final ReadModelFactory factory;

    @Override
    @Transactional
    public void createReadModel(Long listingId,
                                String region,
                                ListingCreatedEvent listingEvent,
                                PropertyEvent propertyEvent, Instant time) {
        ListingKey key = getListingKey(listingId, region);
        PropertyType type = getPropertyType(propertyEvent);
        if(resolver.existReadModel(key, type)){
            throw new ReadModelAlreadyExistsException(key);
        }
        ReadModel model = factory.createReadModel(listingId, region, type, listingEvent, propertyEvent, time);
        resolver.save(model, type);
        log.info("ReadModel с ID {} и регионом {} успешно сохранена", listingId, region);
    }

    @Override
    @Transactional
    public void updateReadModel(Long listingId,
                                String region,
                                ListingUpdatedEvent listingEvent,
                                PropertyEvent propertyEvent, Instant time) {
        PropertyType type = getPropertyType(propertyEvent);
        resolver.updateReadModel(getListingKey(listingId, region), type, listingEvent, time);
        log.info("ReadModel с ID {} и регионом {} успешно обновлена {}", listingId, region, time);
    }

    @Override
    @Transactional
    public void updateProperty(Long listingId,
                               String region,
                               PropertyEvent event, Instant time) {
        PropertyType type = getPropertyType(event);
        resolver.updateProperty(getListingKey(listingId, region), type, event, time);
    }

    @Override
    public void updateDeveloper(Long listingId,
                                String region,
                                DeveloperEvent event, Instant time) {

    }

    @Override
    public void updateLandUse(Long listingId,
                              String region,
                              LandUseEvent event, Instant time) {

    }

    @Override
    public void updateResidentialComplex(Long listingId,
                                         String region,
                                         ResidentialComplexEvent event, Instant time) {

    }

    @Override
    public void changePrice(Long listingId,
                            String region,
                            ChangePriceEvent event, Instant time) {

    }

    @Override
    public void addPromotion(Long listingId,
                             String region,
                             PromotionEvent event, Instant time) {

    }

    @Override
    public void disablePromotion(Long listingId,
                                 String region, Instant time) {

    }

    @Override
    public void addMortgageProgram(Long listingId,
                                   String region,
                                   MortgageProgramsEvent event, Instant time) {

    }

    @Override
    public void removeMortgageProgram(Long listingId,
                                      String region,
                                      MortgageProgramsEvent event, Instant time) {

    }

    @Override
    public void publish(Long listingId,
                        String region, Instant time) {

    }

    @Override
    public void archive(Long listingId,
                        String region, Instant time) {

    }

    @Override
    public void approve(Long listingId,
                        String region, Instant time) {

    }

    @Override
    public void addImage(Long listingId,
                         String region,
                         ImageEvent event, Instant time) {

    }

    @Override
    public void removeImage(Long listingId,
                            String region,
                            ImageEvent event, Instant time) {

    }

    @Override
    public void removeReadModel(Long listingId,
                                String region, Instant time) {

    }

    private ListingKey getListingKey(Long listingId, String region){
        return new ListingKey(listingId, region);
    }

    private PropertyType getPropertyType(PropertyEvent event){
        return event.getPropertyType();
    }
}
