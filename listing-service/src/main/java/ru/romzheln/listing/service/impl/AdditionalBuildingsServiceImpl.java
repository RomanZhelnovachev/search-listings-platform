package ru.romzheln.listing.service.impl;

import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romzheln.listing.dto.request.reference.AdditionalBuildingRequest;
import ru.romzheln.listing.dto.response.AdditionalBuildingResponse;
import ru.romzheln.listing.exception.notFound.AdditionalBuildingsNotFoundException;
import ru.romzheln.listing.mapper.AdditionalBuildingMapper;
import ru.romzheln.listing.model.entity.common.AdditionalBuilding;
import ru.romzheln.listing.repository.AdditionalBuildingRepository;
import ru.romzheln.listing.service.CrudService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdditionalBuildingsServiceImpl
    implements CrudService<
        AdditionalBuilding, AdditionalBuildingRequest, AdditionalBuildingResponse> {

  private final AdditionalBuildingRepository repository;
  private final AdditionalBuildingMapper mapper;

  @Override
  @Transactional
  public AdditionalBuildingResponse create(AdditionalBuildingRequest request) {
    AdditionalBuilding building =
        AdditionalBuilding.builder()
            .name(request.name())
            .description(request.description())
            .build();
    AdditionalBuilding savedBuilding = repository.save(building); 
    log.info("Дополнительная постройка с ID {} успешно сохранена", savedBuilding.getId());
    return mapper.toResponse(savedBuilding);
  }

  @Override
  @Transactional
  public AdditionalBuildingResponse update(Long id, AdditionalBuildingRequest request) {
    AdditionalBuilding building = getAdditionalBuilding(id);
    if (request.name() != null) {
      building.setName(request.name());
    }
    if (request.description() != null) {
      building.setDescription(request.description());
    }
    log.info("Данные по дополнительной постройке с ID {} успешно обновлены", id);
    return mapper.toResponse(building);
  }

  @Override
  @Transactional(readOnly = true)
  public AdditionalBuildingResponse findById(Long id) {
    AdditionalBuilding building = getAdditionalBuilding(id);
    log.info("Дополнительная постройка с ID {} получена", id);
    return mapper.toResponse(building);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<AdditionalBuildingResponse> getAll(Pageable pageable) {
    Page<AdditionalBuilding> buildings = repository.findAll(pageable);
    log.info(
        "Постраничный список дополнительных построек успешно получен: page = {}, size = {}",
        pageable.getPageNumber(),
        pageable.getPageSize());
    return mapper.toPageResponse(buildings);
  }

  private AdditionalBuilding getAdditionalBuilding(Long id) {
    return repository.findById(id).orElseThrow(() -> new AdditionalBuildingsNotFoundException(id));
  }

  public Set<AdditionalBuilding> getAllAdditionalBuildingsByIds(Set<Long> additionalBuildings) {
    return new HashSet<>(repository.findAllById(additionalBuildings));
  }
}
