package ru.romzheln.listing.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.romzheln.listing.dto.event.AdditionalBuildingEvent;
import ru.romzheln.listing.dto.request.reference.AdditionalBuildingRequest;
import ru.romzheln.listing.dto.response.AdditionalBuildingResponse;
import ru.romzheln.listing.exception.AdditionalBuildingsNotFoundException;
import ru.romzheln.listing.mapper.AdditionalBuildingMapper;
import ru.romzheln.listing.model.entity.common.AdditionalBuilding;
import ru.romzheln.listing.model.enums.AggregateType;
import ru.romzheln.listing.model.enums.EventType;
import ru.romzheln.listing.repository.AdditionalBuildingRepository;
import ru.romzheln.listing.service.CrudService;
import ru.romzheln.listing.service.OutboxEventService;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdditionalBuildingsServiceImpl implements CrudService<AdditionalBuilding, AdditionalBuildingRequest, AdditionalBuildingResponse> {

    private final AdditionalBuildingRepository repository;
    private final AdditionalBuildingMapper mapper;
    private final OutboxEventService outboxEventService;

    @Override
    @Transactional
    public AdditionalBuildingResponse create(AdditionalBuildingRequest request) {
        AdditionalBuilding building = AdditionalBuilding.builder()
                .name(request.name())
                .description(request.description())
                .build();
        AdditionalBuilding savedBuilding = repository.save(building);
        publishEvent(EventType.CREATED, savedBuilding);
        log.info("Дополнительная постройка с ID {} успешно сохранена", savedBuilding.getId());
        return mapper.toResponse(savedBuilding);
    }

    @Override
    @Transactional
    public AdditionalBuildingResponse update(Long id, AdditionalBuildingRequest request) {
        AdditionalBuilding building = get(id);
        if(request.name() != null){
            building.setName(request.name());
        }
        if(request.description() != null){
            building.setDescription(request.description());
        }
        publishEvent(EventType.UPDATED, building);
        log.info("Данные по дополнительной постройке с ID {} успешно обновлены", id);
        return mapper.toResponse(building);
    }

    @Override
    @Transactional(readOnly = true)
    public AdditionalBuildingResponse findById(Long id) {
        AdditionalBuilding building = get(id);
        log.info("Дополнительная постройка с ID {} получена", id);
        return mapper.toResponse(building);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdditionalBuildingResponse> getAll(Pageable pageable) {
        Page<AdditionalBuilding> buildings = repository.findAll(pageable);
        log.info("Постраничный список дополнительных построек успешно получен: page = {}, size = {}", pageable.getPageNumber(), pageable.getPageSize());
        return mapper.toPageResponse(buildings);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public AdditionalBuilding get(Long id) {
        return repository.findById(id).orElseThrow(()-> new AdditionalBuildingsNotFoundException(id));
    }

    public Set<AdditionalBuilding> getAllAdditionalBuildingsByIds(Set<Long> additionalBuildings){
        return new HashSet<>(repository.findAllById(additionalBuildings));
    }

    private void publishEvent(EventType type, AdditionalBuilding building){
        outboxEventService.save(AggregateType.ADDITIONAL_BUILDING,
                building.getId(), type, mapper.toEvent(building));
    }
}
