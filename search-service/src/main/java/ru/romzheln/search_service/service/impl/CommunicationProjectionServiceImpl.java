package ru.romzheln.search_service.service.impl;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romzheln.search_service.dto.event.CommunicationEvent;
import ru.romzheln.search_service.exception.CommunicationProjectionNotFoundException;
import ru.romzheln.search_service.model.projection.CommunicationProjection;
import ru.romzheln.search_service.repository.CommunicationProjectionRepository;
import ru.romzheln.search_service.service.CommunicationProjectionService;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunicationProjectionServiceImpl implements CommunicationProjectionService {

    private final CommunicationProjectionRepository repository;

    @Override
    @Transactional
    public CommunicationProjection create(Long id, CommunicationEvent event) {
    CommunicationProjection projection = CommunicationProjection.builder()
            .id(id)
            .communicationType(event.type())
            .build();
    repository.save(projection);
        log.info("Проекции коммуникаций с ID {} успешно сохранена", id);
    return projection;
    }

    @Override
    @Transactional
    public CommunicationProjection update(Long id, CommunicationEvent event) {
        CommunicationProjection projection = getProjection(id);
        boolean changed = false;
    if (event.type() != null && !Objects.equals(projection.getCommunicationType(),
            event.type())) {
      projection.setCommunicationType(event.type());
      changed = true;
        }
        if (changed) {
            log.info("Проекция коммуникации с ID {} успешно изменена",
                    id);
        } else {
            log.warn("Отсутствуют данные для изменения проекции коммуникации с ID {}",
                    id);
        }
        return projection;
    }

    @Override
    @Transactional(readOnly = true)
    public CommunicationProjection findById(Long id) {
        return getProjection(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        CommunicationProjection projection = getProjection(id);
        repository.delete(projection);
        log.info("Проекция коммуникации с ID {} успешно удалена", id);
    }

    private CommunicationProjection getProjection(Long id){
        return repository.findById(id).orElseThrow(()-> new CommunicationProjectionNotFoundException(id));
    }
}
