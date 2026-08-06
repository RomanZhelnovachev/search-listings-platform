package ru.romzheln.listing.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.romzheln.listing.dto.request.reference.PurposeRequest;
import ru.romzheln.listing.dto.response.PurposeResponse;
import ru.romzheln.listing.exception.PurposeNotFoundException;
import ru.romzheln.listing.mapper.PurposeMapper;
import ru.romzheln.listing.model.entity.commercial.Purpose;
import ru.romzheln.listing.model.enums.AggregateType;
import ru.romzheln.listing.model.enums.EventType;
import ru.romzheln.listing.repository.PurposeRepository;
import ru.romzheln.listing.service.CrudService;
import ru.romzheln.listing.service.OutboxEventService;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurposeServiceImpl implements CrudService<Purpose, PurposeRequest, PurposeResponse> {

    private final PurposeRepository repository;
    private final PurposeMapper mapper;
    private final OutboxEventService outboxEventService;

    @Override
    @Transactional
    public PurposeResponse create(PurposeRequest request) {
        Purpose purpose = Purpose.builder()
                .name(request.name())
                .description(request.description())
                .build();
        Purpose savedPurpose = repository.save(purpose);
        publishEvent(EventType.CREATED, savedPurpose);
        log.info("Цель с ID {} успешно сохранена", savedPurpose.getId());
        return mapper.toResponse(savedPurpose);
    }

    @Override
    @Transactional
    public PurposeResponse update(Long id, PurposeRequest request) {
        Purpose purpose = get(id);
        if(request.name() != null){
            purpose.setName(request.name());
        }
        if(request.description() != null){
            purpose.setDescription(request.description());
        }
        publishEvent(EventType.UPDATED, purpose);
        log.info("Цель с ID {} успешно обновлена", id);
        return mapper.toResponse(purpose);
    }

    @Override
    @Transactional(readOnly = true)
    public PurposeResponse findById(Long id) {
        Purpose purpose = get(id);
        log.info("Цель с ID {} успешно получена", id);
        return mapper.toResponse(purpose);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PurposeResponse> getAll(Pageable pageable) {
        Page<Purpose> purposes = repository.findAll(pageable);
        log.info("Постраничный список целей успешно получен: page = {}, size = {}", pageable.getPageNumber(), pageable.getPageSize());
        return mapper.toPageResponse(purposes);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Purpose get(Long id) {
        return repository.findById(id).orElseThrow(() -> new PurposeNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Set<Purpose> getAllPurposesBiIds(Set<Long> purposesIds){
        return new HashSet<>(repository.findAllById(purposesIds));
    }

    private void publishEvent(EventType type, Purpose purpose){
        outboxEventService.save(AggregateType.PURPOSE,
                purpose.getId(), type,
                mapper.toEvent(purpose));
    }
}
