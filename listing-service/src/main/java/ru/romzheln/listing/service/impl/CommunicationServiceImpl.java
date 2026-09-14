package ru.romzheln.listing.service.impl;

import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romzheln.listing.dto.request.reference.CommunicationRequest;
import ru.romzheln.listing.dto.response.CommunicationResponse;
import ru.romzheln.listing.exception.notFound.CommunicationNotFoundException;
import ru.romzheln.listing.mapper.CommunicationMapper;
import ru.romzheln.listing.model.entity.common.Communication;
import ru.romzheln.listing.model.enums.EventType;
import ru.romzheln.listing.repository.CommunicationRepository;
import ru.romzheln.listing.service.CrudService;
import ru.romzheln.listing.service.OutboxEventService;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunicationServiceImpl
    implements CrudService<Communication, CommunicationRequest, CommunicationResponse> {

  private final CommunicationRepository repository;
  private final CommunicationMapper mapper;
  private final OutboxEventService outboxEventService;

  @Override
  @Transactional
  public CommunicationResponse create(CommunicationRequest request) {
    Communication communication =
        Communication.builder()
            .communicationType(request.type())
            .description(request.description())
            .build();
    Communication savedCommunication = repository.save(communication);
    publish(savedCommunication, EventType.CREATED_COMMUNICATION);
    log.info("Коммуникация с ID {} успешно сохранена", savedCommunication.getId());
    return mapper.toResponse(savedCommunication);
  }

  @Override
  @Transactional
  public CommunicationResponse update(Long id, CommunicationRequest request) {
    Communication communication = getCommunication(id);
    if (request.type() != null && communication.getCommunicationType() != request.type()) {
      communication.setCommunicationType(request.type());
    }
    if (request.description() != null) {
      communication.setDescription(request.description());
    }
    publish(communication, EventType.UPDATED_COMMUNICATION);
    log.info("Коммуникация с ID {} успешно обновлена", id);
    return mapper.toResponse(communication);
  }

  @Override
  @Transactional(readOnly = true)
  public CommunicationResponse findById(Long id) {
    Communication communication = getCommunication(id);
    log.info("Коммуникация с ID {} успешно получена", id);
    return mapper.toResponse(communication);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<CommunicationResponse> getAll(Pageable pageable) {
    Page<Communication> communications = repository.findAll(pageable);
    log.info(
        "Постраничный список всех коммуникаций успешно получен: page = {}, size = {}",
        pageable.getPageNumber(),
        pageable.getPageSize());
    return mapper.toPageResponse(communications);
  }

  @Transactional(readOnly = true)
  public Set<Communication> getAllCommunicationsByIds(Set<Long> communications) {
    return new HashSet<>(repository.findAllById(communications));
  }


    private Communication getCommunication(Long id) {
        return repository.findById(id).orElseThrow(() -> new CommunicationNotFoundException(id));
    }

    private void publish(Communication communication, EventType type){
      outboxEventService.save(communication.getId(),null, type, null, mapper.toEvent(communication));
    }
}
