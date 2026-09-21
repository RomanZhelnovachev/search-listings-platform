package ru.romzheln.search_service.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.DeveloperEvent;
import ru.romzheln.search_service.dto.event.LandUseEvent;
import ru.romzheln.search_service.dto.event.ResidentialComplexEvent;
import ru.romzheln.search_service.dto.event.property.ApartmentEvent;
import ru.romzheln.search_service.dto.event.property.LandPlotEvent;
import ru.romzheln.search_service.dto.event.property.PropertyEvent;
import ru.romzheln.search_service.exception.ReadModelNotFoundException;
import ru.romzheln.search_service.exception.UnsupportedFieldException;
import ru.romzheln.search_service.factory.ReadModelFactory;
import ru.romzheln.search_service.model.embeded.ListingKey;
import ru.romzheln.search_service.model.read_model.ListingApartmentReadModel;
import ru.romzheln.search_service.model.read_model.ListingHouseReadModel;
import ru.romzheln.search_service.model.read_model.ListingLandPlotReadModel;
import ru.romzheln.search_service.model.read_model.ReadModel;
import ru.romzheln.search_service.repository.ListingLandPlotReadModelRepository;
import ru.romzheln.search_service.updater.ReadModelPropertyUpdater;
import ru.romzheln.search_service.util.CastUtil;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class LandPlotStrategy implements Strategy<ListingLandPlotReadModel>{

    private final ListingLandPlotReadModelRepository repository;
    private final ReadModelFactory factory;
    private final ReadModelPropertyUpdater propertyUpdater;

    @Override
    public void save(ReadModel model) {
        ListingLandPlotReadModel landPlot = CastUtil.castReadModel(model, ListingLandPlotReadModel.class);
        repository.save(landPlot);
    }

    @Override
    public ListingLandPlotReadModel getReadModel(ListingKey key) {
        return repository.findById(key).orElseThrow(() -> new ReadModelNotFoundException(key));
    }

    @Override
    public ListingLandPlotReadModel getReadModelOrNull(ListingKey key) {
        return repository.findById(key).orElse(null);
    }

    @Override
    public boolean existsReadModel(ListingKey key) {
        return repository.existsById(key);
    }

    @Override
    public void updateProperty(ListingKey key,
                               PropertyEvent event,
                               Instant time) {
        String newRegion = event.getLocation().region();
        ListingLandPlotReadModel model = getReadModel(key);
        LandPlotEvent landPlotEvent = CastUtil.castPropertyEvent(event, LandPlotEvent.class);
        propertyUpdater.updateLandPlot(model, landPlotEvent);
        model.setUpdatedAt(time);
        if (newRegion != null && !key.getRegion().equals(newRegion)) {
            updateRegion(key, newRegion);
        }
    }

    @Override
    public void updateRegion(ListingKey key,
                             String newRegion) {
        ListingLandPlotReadModel oldModel = getReadModel(key);
        ListingLandPlotReadModel newModel = factory.updateRegionLandPlot(newRegion, oldModel);
        repository.save(newModel);
        repository.deleteById(key);
    }

    @Override
    public void updateComplex(ListingKey key,
                              ResidentialComplexEvent event,
                              Instant time) {
        throw new UnsupportedFieldException("Попытка обновить несуществующее поле complex у landPlot");
    }

    @Override
    public void updateLandUse(ListingKey key,
                              LandUseEvent event,
                              Instant time) {
        ListingLandPlotReadModel model = getReadModel(key);
        model.getLandPlot().getCommonLandDetails().setLandUseName(event.name());
        model.setUpdatedAt(time);
    }

    @Override
    public void updateDeveloper(ListingKey key,
                                DeveloperEvent event,
                                Instant time) {
        throw new UnsupportedFieldException("Попытка обновить несуществующее поле developer у landPlot");
    }

    @Override
    public void removeReadModel(ListingKey key) {
        repository.deleteById(key);
    }
}
