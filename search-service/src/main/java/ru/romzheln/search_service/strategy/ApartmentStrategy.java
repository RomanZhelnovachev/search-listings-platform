package ru.romzheln.search_service.strategy;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.DeveloperEvent;
import ru.romzheln.search_service.dto.event.LandUseEvent;
import ru.romzheln.search_service.dto.event.ResidentialComplexEvent;
import ru.romzheln.search_service.dto.event.property.ApartmentEvent;
import ru.romzheln.search_service.dto.event.property.PropertyEvent;
import ru.romzheln.search_service.exception.ReadModelNotFoundException;
import ru.romzheln.search_service.exception.UnsupportedFieldException;
import ru.romzheln.search_service.factory.ReadModelFactory;
import ru.romzheln.search_service.model.embeded.ListingKey;
import ru.romzheln.search_service.model.read_model.ListingApartmentReadModel;
import ru.romzheln.search_service.model.read_model.ReadModel;
import ru.romzheln.search_service.repository.ListingApartmentReadModelRepository;
import ru.romzheln.search_service.updater.ReadModelPropertyUpdater;
import ru.romzheln.search_service.util.CastUtil;

@Component
@RequiredArgsConstructor
public class ApartmentStrategy implements Strategy<ListingApartmentReadModel> {

  private final ListingApartmentReadModelRepository repository;
  private final ReadModelFactory factory;
  private final ReadModelPropertyUpdater propertyUpdater;

  @Override
  public void save(ReadModel model) {
    ListingApartmentReadModel apartment =
        CastUtil.castReadModel(model, ListingApartmentReadModel.class);
    repository.save(apartment);
  }

  @Override
  public ListingApartmentReadModel getReadModel(ListingKey key) {
    return repository.findById(key).orElseThrow(() -> new ReadModelNotFoundException(key));
  }

  @Override
  public ListingApartmentReadModel getReadModelOrNull(ListingKey key) {
    return repository.findById(key).orElse(null);
  }

  @Override
  public boolean existsReadModel(ListingKey key) {
    return repository.existsById(key);
  }

  @Override
  public void updateProperty(ListingKey key, PropertyEvent event, Instant time) {
    String newRegion = event.getLocation().region();
    ListingApartmentReadModel model = getReadModel(key);
    ApartmentEvent apartmentEvent = CastUtil.castPropertyEvent(event, ApartmentEvent.class);
    propertyUpdater.updateApartment(model, apartmentEvent);
    model.setUpdatedAt(time);
    if (newRegion != null && !key.getRegion().equals(newRegion)) {
      updateRegion(key, newRegion);
    }
  }

  @Override
  public void updateRegion(ListingKey key, String newRegion) {
    ListingApartmentReadModel oldModel = getReadModel(key);
    ListingApartmentReadModel newModel = factory.updateRegionApartment(newRegion, oldModel);
    repository.save(newModel);
    repository.deleteById(key);
  }

    @Override
    public void updateComplex(ListingKey key,
                              ResidentialComplexEvent event,
                              Instant time) {
        ListingApartmentReadModel model = getReadModel(key);
        model.getApartment().getComplex().setComplexName(event.name());
        model.setUpdatedAt(time);
    }

    @Override
    public void updateLandUse(ListingKey key,
                              LandUseEvent event,
                              Instant time) {
      throw new UnsupportedFieldException("Попытка обновить несуществующее поле landUse у apartment");
    }

    @Override
  public void updateDeveloper(ListingKey key, DeveloperEvent event, Instant time) {
    ListingApartmentReadModel model = getReadModel(key);
    model.getApartment().getDeveloper().setDeveloperName(event.name());
    model.setUpdatedAt(time);
  }

    @Override
    public void removeReadModel(ListingKey key) {
      repository.deleteById(key);
    }
}
