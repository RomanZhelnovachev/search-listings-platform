package ru.romzheln.listing.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.romzheln.listing.dto.request.reference.CommunicationRequest;
import ru.romzheln.listing.dto.response.CommunicationResponse;
import ru.romzheln.listing.exception.CommunicationNotFoundException;
import ru.romzheln.listing.mapper.CommunicationMapper;
import ru.romzheln.listing.model.entity.common.Communication;
import ru.romzheln.listing.model.enums.AggregateType;
import ru.romzheln.listing.model.enums.EventType;
import ru.romzheln.listing.repository.CommunicationRepository;
import ru.romzheln.listing.service.CrudService;
import ru.romzheln.listing.service.OutboxEventService;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunicationServiceImpl implements CrudService<Communication, CommunicationRequest, CommunicationResponse> {

    private final CommunicationRepository repository;
    private final CommunicationMapper mapper;
    private final OutboxEventService outboxEventService;

    @Override
    @Transactional
    public CommunicationResponse create(CommunicationRequest request) {
        Communication communication = Communication.builder()
                .communicationType(request.type())
                .description(request.description())
                .build();
        Communication savedCommunication = repository.save(communication);
        publishEvent(EventType.CREATED, savedCommunication);
        log.info("Коммуникация с ID {} успешно сохранена", savedCommunication.getId());
        return mapper.toResponse(savedCommunication);
    }

    @Override
    @Transactional
    public CommunicationResponse update(Long id, CommunicationRequest request) {
        Communication communication = get(id);
        if(request.type() != null && communication.getCommunicationType() != request.type()){
            communication.setCommunicationType(request.type());
        }
        if(request.description() != null){
            communication.setDescription(request.description());
        }
        publishEvent(EventType.UPDATED, communication);
        log.info("Коммуникация с ID {} успешно обновлена", id);
        return mapper.toResponse(communication);
    }

    @Override
    @Transactional(readOnly = true)
    public CommunicationResponse findById(Long id) {
        Communication communication = get(id);
        log.info("Коммуникация с ID {} успешно получена", id);
        return mapper.toResponse(communication);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommunicationResponse> getAll(Pageable pageable) {
        Page<Communication> communications = repository.findAll(pageable);
        log.info("Постраничный список всех коммуникаций успешно получен: page = {}, size = {}", pageable.getPageNumber(), pageable.getPageSize());
        return mapper.toPageResponse(communications);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Communication get(Long id) {
        return repository.findById(id).orElseThrow(()-> new CommunicationNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Set<Communication> getAllCommunicationsByIds(Set<Long> communications){
        return new HashSet<>(repository.findAllById(communications));
    }

    private void publishEvent(EventType type, Communication communication){
        outboxEventService.save(AggregateType.COMMUNICATION,
                communication.getId(), type,
                mapper.toEvent(communication));
    }
}
