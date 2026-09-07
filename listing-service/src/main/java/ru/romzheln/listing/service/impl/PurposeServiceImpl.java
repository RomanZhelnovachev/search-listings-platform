package ru.romzheln.listing.service.impl;

import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romzheln.listing.dto.request.reference.PurposeRequest;
import ru.romzheln.listing.dto.response.PurposeResponse;
import ru.romzheln.listing.exception.notFound.PurposeNotFoundException;
import ru.romzheln.listing.mapper.PurposeMapper;
import ru.romzheln.listing.model.entity.commercial.Purpose;
import ru.romzheln.listing.model.enums.EventType;
import ru.romzheln.listing.repository.PurposeRepository;
import ru.romzheln.listing.service.CrudService;
import ru.romzheln.listing.service.OutboxEventService;

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
    Purpose purpose =
        Purpose.builder().name(request.name()).description(request.description()).build();
    Purpose savedPurpose = repository.save(purpose);
    publish(savedPurpose, EventType.CREATED_PURPOSE);
    log.info("Цель с ID {} успешно сохранена", savedPurpose.getId());
    return mapper.toResponse(savedPurpose);
  }

  @Override
  @Transactional
  public PurposeResponse update(Long id, PurposeRequest request) {
    Purpose purpose = getPurpose(id);
    if (request.name() != null) {
      purpose.setName(request.name());
      publish(purpose, EventType.UPDATED_PURPOSE);
    }
    if (request.description() != null) {
      purpose.setDescription(request.description());
    }
    log.info("Цель с ID {} успешно обновлена", id);
    return mapper.toResponse(purpose);
  }

  @Override
  @Transactional(readOnly = true)
  public PurposeResponse findById(Long id) {
    Purpose purpose = getPurpose(id);
    log.info("Цель с ID {} успешно получена", id);
    return mapper.toResponse(purpose);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<PurposeResponse> getAll(Pageable pageable) {
    Page<Purpose> purposes = repository.findAll(pageable);
    log.info(
        "Постраничный список целей успешно получен: page = {}, size = {}",
        pageable.getPageNumber(),
        pageable.getPageSize());
    return mapper.toPageResponse(purposes);
  }

  @Transactional(readOnly = true)
  public Set<Purpose> getAllPurposesBiIds(Set<Long> purposesIds) {
    return new HashSet<>(repository.findAllById(purposesIds));
  }


    private Purpose getPurpose(Long id) {
        return repository.findById(id).orElseThrow(() -> new PurposeNotFoundException(id));
    }

    private void publish(Purpose purpose, EventType type){
        outboxEventService.save(purpose.getId(), type, null, mapper.toEvent(purpose));
    }
}
