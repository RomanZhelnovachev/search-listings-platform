package ru.romzheln.search_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.romzheln.search_service.dto.event.DeveloperEvent;
import ru.romzheln.search_service.dto.event.LandUseEvent;
import ru.romzheln.search_service.dto.event.ResidentialComplexEvent;
import ru.romzheln.search_service.dto.event.listing.*;
import ru.romzheln.search_service.dto.event.property.PropertyEvent;
import ru.romzheln.search_service.resolver.ReadModelResolver;
import ru.romzheln.search_service.service.ListingReadModelService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListingReadModelServiceImpl implements ListingReadModelService {

    private final ReadModelResolver resolver;

    @Override
    public void createReadModel(Long listingId,
                                String region,
                                ListingCreatedEvent listingEvent,
                                PropertyEvent propertyEvent) {

    }

    @Override
    public void updateReadModel(Long listingId,
                                String region,
                                ListingUpdatedEvent listingEvent,
                                PropertyEvent propertyEvent) {

    }

    @Override
    public void updateProperty(Long listingId,
                               String region,
                               PropertyEvent event) {

    }

    @Override
    public void updateDeveloper(Long listingId,
                                String region,
                                DeveloperEvent event) {

    }

    @Override
    public void updateLandUse(Long listingId,
                              String region,
                              LandUseEvent event) {

    }

    @Override
    public void updateResidentialComplex(Long listingId,
                                         String region,
                                         ResidentialComplexEvent event) {

    }

    @Override
    public void changePrice(Long listingId,
                            String region,
                            ChangePriceEvent event) {

    }

    @Override
    public void addPromotion(Long listingId,
                             String region,
                             PromotionEvent event) {

    }

    @Override
    public void disablePromotion(Long listingId,
                                 String region) {

    }

    @Override
    public void addMortgageProgram(Long listingId,
                                   String region,
                                   MortgageProgramsEvent event) {

    }

    @Override
    public void removeMortgageProgram(Long listingId,
                                      String region,
                                      MortgageProgramsEvent event) {

    }

    @Override
    public void publish(Long listingId,
                        String region) {

    }

    @Override
    public void archive(Long listingId,
                        String region) {

    }

    @Override
    public void approve(Long listingId,
                        String region) {

    }

    @Override
    public void addImage(Long listingId,
                         String region,
                         ImageEvent event) {

    }

    @Override
    public void removeImage(Long listingId,
                            String region,
                            ImageEvent event) {

    }

    @Override
    public void removeReadModel(Long listingId,
                                String region) {

    }
}
