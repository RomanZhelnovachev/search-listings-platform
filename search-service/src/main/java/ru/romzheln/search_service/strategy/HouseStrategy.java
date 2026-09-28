package ru.romzheln.search_service.strategy;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.DeveloperEvent;
import ru.romzheln.search_service.dto.event.LandUseEvent;
import ru.romzheln.search_service.dto.event.ResidentialComplexEvent;
import ru.romzheln.search_service.dto.event.property.HouseEvent;
import ru.romzheln.search_service.dto.event.property.PropertyEvent;
import ru.romzheln.search_service.exception.ReadModelNotFoundException;
import ru.romzheln.search_service.factory.ReadModelFactory;
import ru.romzheln.search_service.model.embeded.ListingKey;
import ru.romzheln.search_service.model.read_model.ListingHouseReadModel;
import ru.romzheln.search_service.model.read_model.ReadModel;
import ru.romzheln.search_service.repository.ListingHouseReadModelRepository;
import ru.romzheln.search_service.updater.ReadModelPropertyUpdater;
import ru.romzheln.search_service.util.CastUtil;

@Component
@RequiredArgsConstructor
public class HouseStrategy implements Strategy<ListingHouseReadModel> {

  private final ListingHouseReadModelRepository repository;
  private final ReadModelFactory factory;
  private final ReadModelPropertyUpdater propertyUpdater;

  @Override
  public void save(ReadModel model) {
    ListingHouseReadModel house = CastUtil.castReadModel(model, ListingHouseReadModel.class);
    repository.save(house);
  }

  @Override
  public ListingHouseReadModel getReadModel(ListingKey key) {
    return repository.findById(key).orElseThrow(() -> new ReadModelNotFoundException(key));
  }

  @Override
  public ListingHouseReadModel getReadModelOrNull(ListingKey key) {
    return repository.findById(key).orElse(null);
  }

  @Override
  public boolean existsReadModel(ListingKey key) {
    return repository.existsById(key);
  }

  @Override
  public void updateProperty(ListingKey key, PropertyEvent event, Instant time) {
    String newRegion = event.getLocation().region();
    ListingHouseReadModel model = getReadModel(key);
    HouseEvent houseEvent = CastUtil.castPropertyEvent(event, HouseEvent.class);
    propertyUpdater.updateHouse(model, houseEvent);
    model.setUpdatedAt(time);
    if (newRegion != null && !key.getRegion().name().equals(newRegion)) {
      updateRegion(key, newRegion);
    }
  }

  @Override
  public void updateRegion(ListingKey key, String newRegion) {
    ListingHouseReadModel oldModel = getReadModel(key);
    ListingHouseReadModel newModel = factory.updateRegionHouse(newRegion, oldModel);
    repository.save(newModel);
    repository.deleteById(key);
  }

  @Override
  public void updateComplex(ListingKey key, ResidentialComplexEvent event, Instant time) {
    ListingHouseReadModel model = getReadModel(key);
    model.getHouse().getComplex().setComplexName(event.name());
    model.setUpdatedAt(time);
  }

  @Override
  public void updateLandUse(ListingKey key, LandUseEvent event, Instant time) {
    ListingHouseReadModel model = getReadModel(key);
    model.getHouse().getCommonLandDetails().setLandUseName(event.name());
    model.setUpdatedAt(time);
  }

  @Override
  public void updateDeveloper(ListingKey key, DeveloperEvent event, Instant time) {
      ListingHouseReadModel model = getReadModel(key);
      model.getHouse().getDeveloper().setDeveloperName(event.name());
      model.setUpdatedAt(time);
  }

  @Override
  public void removeReadModel(ListingKey key) {
      repository.deleteById(key);
  }
}
