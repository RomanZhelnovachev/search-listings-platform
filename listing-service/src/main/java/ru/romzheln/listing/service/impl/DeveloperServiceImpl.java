package ru.romzheln.listing.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.romzheln.listing.dto.request.reference.DeveloperRequest;
import ru.romzheln.listing.dto.response.DeveloperResponse;
import ru.romzheln.listing.exception.notFound.DeveloperNotFoundException;
import ru.romzheln.listing.mapper.DeveloperMapper;
import ru.romzheln.listing.model.entity.common.Developer;
import ru.romzheln.listing.model.enums.AggregateType;
import ru.romzheln.listing.model.enums.EventType;
import ru.romzheln.listing.repository.DeveloperRepository;
import ru.romzheln.listing.service.CrudService;
import ru.romzheln.listing.service.OutboxEventService;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeveloperServiceImpl implements CrudService<Developer, DeveloperRequest, DeveloperResponse> {

    private final DeveloperRepository repository;
    private final DeveloperMapper mapper;
    private final OutboxEventService outboxEventService;

    @Override
    @Transactional
    public DeveloperResponse create(DeveloperRequest request) {
        Developer developer = Developer.builder()
                .name(request.name())
                .build();
        Developer savedDeveloper = repository.save(developer);
        publishEvent(EventType.CREATED, savedDeveloper);
        log.info("Застройщик с ID {} успешно сохранён", savedDeveloper.getId());
        return mapper.toResponse(savedDeveloper);
    }

    @Override
    @Transactional
    public DeveloperResponse update(Long id, DeveloperRequest request) {
        Developer developer = get(id);
        developer.setName(request.name());
        publishEvent(EventType.UPDATED, developer);
        log.info("Название застройщика с ID {} изменено на {}", id, developer.getName());
        return mapper.toResponse(developer);
    }

    @Override
    @Transactional(readOnly = true)
    public DeveloperResponse findById(Long id) {
        Developer developer = get(id);
        log.info("Получен застройщик с ID {}", id);
        return mapper.toResponse(developer);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeveloperResponse> getAll(Pageable pageable) {
        Page<Developer> developers = repository.findAll(pageable);
        log.info("Получен список застройщиков: page={}, size={}",
                pageable.getPageNumber(),
                pageable.getPageSize());
        return mapper.toPageResponse(developers);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Developer get(Long id) {
        return repository.findById(id).orElseThrow(()-> new DeveloperNotFoundException(id));
    }

    private void publishEvent(EventType type, Developer developer){
        outboxEventService.save(AggregateType.DEVELOPER,
                developer.getId(), type, mapper.toEvent(developer));
    }
}
