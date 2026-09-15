package ru.romzheln.search_service.resolver;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.DeveloperEvent;
import ru.romzheln.search_service.dto.event.listing.ListingUpdatedEvent;
import ru.romzheln.search_service.dto.event.property.*;
import ru.romzheln.search_service.exception.ReadModelNotFoundException;
import ru.romzheln.search_service.exception.UnknownPropertyType;
import ru.romzheln.search_service.model.embeded.Listing;
import ru.romzheln.search_service.model.embeded.ListingKey;
import ru.romzheln.search_service.model.enums.PropertyType;
import ru.romzheln.search_service.model.read_model.*;
import ru.romzheln.search_service.repository.ListingApartmentReadModelRepository;
import ru.romzheln.search_service.repository.ListingCommercialReadModelRepository;
import ru.romzheln.search_service.repository.ListingHouseReadModelRepository;
import ru.romzheln.search_service.repository.ListingLandPlotReadModelRepository;
import ru.romzheln.search_service.updater.ReadModelPropertyUpdater;

@Component
@RequiredArgsConstructor
public class ReadModelResolver {

    private final ListingApartmentReadModelRepository apartmentRepository;
    private final ListingCommercialReadModelRepository commercialRepository;
    private final ListingHouseReadModelRepository houseRepository;
    private final ListingLandPlotReadModelRepository landPlotRepository;
    private final ReadModelPropertyUpdater propertyUpdater;

    public boolean existReadModel(ListingKey key, PropertyType type){
        switch (type){
            case APARTMENT -> {
                return apartmentRepository.existsById(key);
            }
            case COMMERCIAL -> {
                return commercialRepository.existsById(key);
            }
            case HOUSE -> {
                return houseRepository.existsById(key);
            }
            case LAND_PLOT -> {
                return landPlotRepository.existsById(key);
            }
        }
        throw new UnknownPropertyType(type);
    }

    public void save(ReadModel model, PropertyType type){
        switch (type){
            case APARTMENT -> apartmentRepository.save((ListingApartmentReadModel) model);
            case COMMERCIAL -> commercialRepository.save((ListingCommercialReadModel) model);
            case HOUSE -> houseRepository.save((ListingHouseReadModel) model);
            case LAND_PLOT -> landPlotRepository.save((ListingLandPlotReadModel) model);
        }
    }

    public void updateReadModel(
            ListingKey key,
            PropertyType type,
            ListingUpdatedEvent event,
            Instant time) {

        switch (type) {
            case APARTMENT -> {
                ListingApartmentReadModel model = getApartmentModel(key);
                updateListing(model, event, time);
            }
            case COMMERCIAL -> {
                ListingCommercialReadModel model = getCommercialModel(key);
                updateListing(model, event, time);
            }
            case HOUSE -> {
                ListingHouseReadModel model = getHouseModel(key);
                updateListing(model, event, time);
            }
            case LAND_PLOT -> {
                ListingLandPlotReadModel model = getLandPlotModel(key);
                updateListing(model, event, time);
            }
        }
    }

    public void updateProperty(ListingKey key,
                               PropertyType type,
                               PropertyEvent event,
                               Instant time) {
        switch (type){
            case APARTMENT -> {
                ListingApartmentReadModel model = getApartmentModel(key);
                ApartmentEvent eventImpl = (ApartmentEvent) event;
                propertyUpdater.updateApartment(model, eventImpl);
                model.setUpdatedAt(time);
            }
            case COMMERCIAL -> {
                ListingCommercialReadModel model = getCommercialModel(key);
                CommercialEvent eventImpl = (CommercialEvent) event;
                propertyUpdater.updateCommercial(model, eventImpl);
                model.setUpdatedAt(time);
            }
            case HOUSE -> {
                ListingHouseReadModel model = getHouseModel(key);
                HouseEvent eventImpl = (HouseEvent) event;
                propertyUpdater.updateHouse(model, eventImpl);
                model.setUpdatedAt(time);
            }
            case LAND_PLOT -> {
                ListingLandPlotReadModel model = getLandPlotModel(key);
                LandPlotEvent eventImpl = (LandPlotEvent) event;
                propertyUpdater.updateLandPlot(model, eventImpl);
                model.setUpdatedAt(time);
            }
        }

    }

    public void updateDeveloper(ListingKey key, DeveloperEvent event, Instant time){

    }

    private void updateListing(ReadModel model, ListingUpdatedEvent event, Instant time){
        Listing listing = model.getListing();
        boolean changed = false;
        if(event.title() != null && !event.title().equals(listing.getTitle())){
      listing.setTitle(event.title());
      changed = true;
        }
        if(event.description() != null && !event.description().equals(listing.getDescription())){
            listing.setDescription(event.description());
            changed = true;
        }
        if(event.dealType() != null && !event.dealType().equals(listing.getDealType())){
            listing.setDealType(event.dealType());
            changed = true;
        }
        if(changed){
            model.setUpdatedAt(time);
        }
    }

    private ListingApartmentReadModel getApartmentModel(ListingKey key){
        return apartmentRepository.findById(key).orElseThrow(()-> new ReadModelNotFoundException(key));
    }

    private ListingCommercialReadModel getCommercialModel(ListingKey key){
        return commercialRepository.findById(key).orElseThrow(()-> new ReadModelNotFoundException(key));
    }

    private ListingHouseReadModel getHouseModel(ListingKey key){
        return houseRepository.findById(key).orElseThrow(()-> new ReadModelNotFoundException(key));
    }

    private ListingLandPlotReadModel getLandPlotModel(ListingKey key){
        return landPlotRepository.findById(key).orElseThrow(()-> new ReadModelNotFoundException(key));
    }
}
