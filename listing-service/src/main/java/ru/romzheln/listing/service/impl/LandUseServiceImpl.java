package ru.romzheln.listing.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.romzheln.listing.dto.request.reference.LandUseRequest;
import ru.romzheln.listing.dto.response.LandUseResponse;
import ru.romzheln.listing.exception.notFound.LandUseNotFoundException;
import ru.romzheln.listing.mapper.LandUseMapper;
import ru.romzheln.listing.model.entity.common.LandUse;
import ru.romzheln.listing.model.enums.AggregateType;
import ru.romzheln.listing.model.enums.EventType;
import ru.romzheln.listing.repository.LandUseRepository;
import ru.romzheln.listing.service.CrudService;
import ru.romzheln.listing.service.OutboxEventService;

@Service
@RequiredArgsConstructor
@Slf4j
public class LandUseServiceImpl implements CrudService<LandUse, LandUseRequest, LandUseResponse> {

    private final LandUseRepository repository;
    private final LandUseMapper mapper;
    private final OutboxEventService outboxEventService;

    @Override
    @Transactional
    public LandUseResponse create(LandUseRequest request) {
        LandUse landUse = LandUse.builder()
                .name(request.name())
                .description(request.description())
                .build();
        LandUse savedLandUse = repository.save(landUse);
        publishEvent(EventType.CREATED, savedLandUse);
        log.info("Назначение земли с ID {} успешно сохранено", savedLandUse.getId());
        return mapper.toResponse(savedLandUse);
    }

    @Override
    @Transactional
    public LandUseResponse update(Long id, LandUseRequest request) {
        LandUse landUse = get(id);
        if(request.name() != null){
            landUse.setName(request.name());
        }
        if(request.description() != null){
            landUse.setDescription(request.description());
        }
        publishEvent(EventType.UPDATED, landUse);
        log.info("Назначение земли с ID {} успешно обновлено", id);
        return mapper.toResponse(landUse);
    }

    @Override
    @Transactional(readOnly = true)
    public LandUseResponse findById(Long id) {
        LandUse landUse = get(id);
        log.info("Назначение земли с ID {} успешно получено", id);
        return mapper.toResponse(landUse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LandUseResponse> getAll(Pageable pageable) {
        Page<LandUse> landUses = repository.findAll(pageable);
        log.info("Получен постраничный список всех назначений земли: page = {}, size = {}", pageable.getPageNumber(), pageable.getPageSize());
        return mapper.toPageResponse(landUses);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public LandUse get(Long id) {
        return repository.findById(id).orElseThrow(()-> new LandUseNotFoundException(id));
    }

    private void publishEvent(EventType type, LandUse landUse){
        outboxEventService.save(AggregateType.LAND_USE,
                landUse.getId(), type,
                mapper.toEvent(landUse));
    }
}
