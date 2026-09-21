package ru.romzheln.search_service.strategy;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.DeveloperEvent;
import ru.romzheln.search_service.dto.event.LandUseEvent;
import ru.romzheln.search_service.dto.event.ResidentialComplexEvent;
import ru.romzheln.search_service.dto.event.property.CommercialEvent;
import ru.romzheln.search_service.dto.event.property.PropertyEvent;
import ru.romzheln.search_service.exception.ReadModelNotFoundException;
import ru.romzheln.search_service.exception.UnsupportedFieldException;
import ru.romzheln.search_service.factory.ReadModelFactory;
import ru.romzheln.search_service.model.embeded.ListingKey;
import ru.romzheln.search_service.model.read_model.ListingCommercialReadModel;
import ru.romzheln.search_service.model.read_model.ReadModel;
import ru.romzheln.search_service.repository.ListingCommercialReadModelRepository;
import ru.romzheln.search_service.updater.ReadModelPropertyUpdater;
import ru.romzheln.search_service.util.CastUtil;

@Component
@RequiredArgsConstructor
public class CommercialStrategy implements Strategy<ListingCommercialReadModel>{

    private final ListingCommercialReadModelRepository repository;
    private final ReadModelFactory factory;
    private final ReadModelPropertyUpdater propertyUpdater;

    @Override
    public void save(ReadModel model) {
        ListingCommercialReadModel commercial = CastUtil.castReadModel(model, ListingCommercialReadModel.class);
        repository.save(commercial);
    }

    @Override
    public ListingCommercialReadModel getReadModel(ListingKey key) {
        return repository.findById(key).orElseThrow(() -> new ReadModelNotFoundException(key));
    }

    @Override
    public ListingCommercialReadModel getReadModelOrNull(ListingKey key) {
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
        ListingCommercialReadModel model = getReadModel(key);
        CommercialEvent commercialEvent = CastUtil.castPropertyEvent(event, CommercialEvent.class);
        propertyUpdater.updateCommercial(model, commercialEvent);
        model.setUpdatedAt(time);
        if (newRegion != null && !key.getRegion().equals(newRegion)) {
            updateRegion(key, newRegion);
        }
    }

    @Override
    public void updateRegion(ListingKey key,
                             String newRegion) {
        ListingCommercialReadModel oldModel = getReadModel(key);
        ListingCommercialReadModel newModel = factory.updateRegionCommercial(newRegion, oldModel);
        repository.save(newModel);
        repository.deleteById(key);
    }

    @Override
    public void updateComplex(ListingKey key,
                              ResidentialComplexEvent event,
                              Instant time) {
        throw new UnsupportedFieldException("Попытка обновить несуществующее поле complex у commercial");
    }

    @Override
    public void updateLandUse(ListingKey key,
                              LandUseEvent event,
                              Instant time) {
        throw new UnsupportedFieldException("Попытка обновить несуществующее поле landUse у commercial");
    }

    @Override
    public void updateDeveloper(ListingKey key,
                                DeveloperEvent event,
                                Instant time) {
        throw new UnsupportedFieldException("Попытка обновить несуществующее поле developer у commercial");
    }

    @Override
    public void removeReadModel(ListingKey key) {
        repository.deleteById(key);
    }
}
