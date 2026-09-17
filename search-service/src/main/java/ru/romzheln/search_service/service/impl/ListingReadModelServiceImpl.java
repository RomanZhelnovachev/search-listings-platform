package ru.romzheln.search_service.service.impl;

import java.time.Instant;
import java.util.Map;
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
import ru.romzheln.search_service.model.embeded.Listing;
import ru.romzheln.search_service.model.embeded.ListingKey;
import ru.romzheln.search_service.model.enums.PropertyType;
import ru.romzheln.search_service.model.read_model.ReadModel;
import ru.romzheln.search_service.service.ListingReadModelService;
import ru.romzheln.search_service.strategy.Strategy;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListingReadModelServiceImpl implements ListingReadModelService {

    private final Map<PropertyType, Strategy<?>> strategies;
    private final ReadModelFactory factory;

    @Override
    @Transactional
    public void createReadModel(Long listingId,
                                String region, PropertyType type,
                                ListingCreatedEvent listingEvent,
                                PropertyEvent propertyEvent, Instant time) {
        ListingKey key = getListingKey(listingId, region);
        Strategy<?> strategy = getStrategy(type);
        if(existReadModel(key, type)){
            throw new ReadModelAlreadyExistsException(key);
        }
        ReadModel model = factory.createReadModel(listingId, region, type, listingEvent, propertyEvent, time);
        strategy.save(model);
        log.info("Объявление с ID {} и регионом {} успешно сохранена {}", listingId, region, time);
    }

    @Override
    @Transactional
    public void updateReadModel(Long listingId,
                                String region, PropertyType type,
                                ListingUpdatedEvent listingEvent,
                                PropertyEvent propertyEvent, Instant time) {
        ReadModel model = getModel(type, getListingKey(listingId, region));
        Listing listing = model.getListing();
        updateListing(listing, listingEvent);
        model.setUpdatedAt(time);
        log.info("Объявление с ID {} и регионом {} успешно обновлено {}", listingId, region, time);
    }

    @Override
    @Transactional
    public void updateProperty(Long listingId,
                               String region,
                               PropertyType type,
                               PropertyEvent event, Instant time) {
        Strategy<?> strategy = getStrategy(type);
        strategy.updateProperty(getListingKey(listingId, region), event, time);
        log.info("Информация в объявлении с ID {} и регионом {} об объекте недвижимости успешно обновлена {}", listingId, region, time);
    }

    @Override
    @Transactional
    public void updateDeveloper(Long listingId,
                                String region,
                                PropertyType type,
                                DeveloperEvent event, Instant time) {
        Strategy<?> strategy = getStrategy(type);
        strategy.updateDeveloper(getListingKey(listingId, region), event, time);
        log.info("Информация в объявлении с ID {} и регионом {} о застройщике успешно обновлена {}", listingId, region, time);
    }

    @Override
    @Transactional
    public void updateLandUse(Long listingId,
                              String region,
                              PropertyType type,
                              LandUseEvent event, Instant time) {
        Strategy<?> strategy = getStrategy(type);
        strategy.updateLandUse(getListingKey(listingId, region), event, time);
        log.info("Информация в объявлении с ID {} и регионом {} о назначении земли успешно обновлена {}", listingId, region, time);
    }

    @Override
    @Transactional
    public void updateResidentialComplex(Long listingId,
                                         String region,
                                         PropertyType type,
                                         ResidentialComplexEvent event, Instant time) {
        Strategy<?> strategy = getStrategy(type);
        strategy.updateComplex(getListingKey(listingId, region), event, time);
        log.info("Информация в объявлении с ID {} и регионом {} о жилом комплексе успешно обновлена {}", listingId, region, time);
    }

    @Override
    @Transactional
    public void changePrice(Long listingId,
                            String region,
                            PropertyType type,
                            ChangePriceEvent event, Instant time) {
        ReadModel model = getModel(type, getListingKey(listingId, region));
        model.getListing().setPrice(event.newPrice());
        model.setUpdatedAt(time);
        log.info("Цена объекта в объявлении с ID {} и регионом {} успешно изменена {}", listingId, region, time);
    }

    @Override
    @Transactional
    public void addPromotion(Long listingId,
                             String region,
                             PropertyType type,
                             PromotionEvent event, Instant time) {
        ReadModel model = getModel(type, getListingKey(listingId, region));
    model.getListing().setPromotionId(event.promotionId());
    model.setUpdatedAt(time);
        log.info("Объявлению с ID {} и регионом {} успешно подключена промоакция с ID{} -  {}", listingId, region, event.promotionId(), time);
  }

  @Override
  @Transactional
  public void disablePromotion(Long listingId, String region, PropertyType type, Instant time) {
      ReadModel model = getModel(type, getListingKey(listingId, region));
      model.getListing().setPromotionId(null);
      model.setUpdatedAt(time);
      log.info("Объявлению с ID {} и регионом {} успешно отключена промоакция -  {}", listingId, region, time);
  }

    @Override
    @Transactional
    public void changeMortgageProgram(Long listingId,
                                   String region,
                                   PropertyType type,
                                   MortgageProgramsEvent event, Instant time) {
        ReadModel model = getModel(type, getListingKey(listingId, region));
        model.getListing().setMortgageProgramIds(event.mortgagePrograms());
        model.setUpdatedAt(time);
    }

    @Override
    @Transactional
    public void publish(Long listingId,
                        String region, PropertyType type, Instant time) {
        ReadModel model = getModel(type, getListingKey(listingId, region));
        model.setIncludedInSearch(true);
        model.setUpdatedAt(time);
    }

    @Override
    @Transactional
    public void archive(Long listingId,
                        String region, PropertyType type, Instant time) {
        ReadModel model = getModel(type, getListingKey(listingId, region));
        model.setIncludedInSearch(false);
        model.setUpdatedAt(time);
    }

    @Override
    @Transactional
    public void approve(Long listingId,
                        String region, PropertyType type, Instant time) {
        ReadModel model = getModel(type, getListingKey(listingId, region));
        model.setIncludedInSearch(true);
        model.setUpdatedAt(time);
    }

    @Override
    @Transactional
    public void changeImages(Long listingId,
                         String region,
                         PropertyType type,
                         ImageEvent event, Instant time) {
        ReadModel model = getModel(type, getListingKey(listingId, region));
        model.getListing().setImageIds(event.images());
        model.setUpdatedAt(time);
    }

    @Override
    @Transactional
    public void removeReadModel(Long listingId,
                                String region, PropertyType type) {
        Strategy<?> strategy = getStrategy(type);
        strategy.removeReadModel(getListingKey(listingId, region));
    }

    private ListingKey getListingKey(Long listingId, String region){
        return new ListingKey(listingId, region);
    }

    private boolean existReadModel(ListingKey key, PropertyType type) {
        Strategy<?> strategy = getStrategy(type);
        return strategy.existsReadModel(key);
    }

    private void updateListing(Listing listing, ListingUpdatedEvent event) {
        if (event.title() != null && !event.title().equals(listing.getTitle())) {
            listing.setTitle(event.title());
        }
        if (event.description() != null && !event.description().equals(listing.getDescription())) {
            listing.setDescription(event.description());
        }
        if (event.dealType() != null && !event.dealType().equals(listing.getDealType())) {
            listing.setDealType(event.dealType());
        }
    }

    private Strategy<?> getStrategy(PropertyType type) {
        return strategies.get(type);
    }

    @SuppressWarnings("unchecked")
    private <M extends ReadModel> Strategy<M> getStrategyCast(PropertyType type) {
        return (Strategy<M>) strategies.get(type);
    }

    private ReadModel getModel(PropertyType type, ListingKey key){
        Strategy<?> strategy = getStrategy(type);
        return strategy.getReadModel(key);
    }
}
