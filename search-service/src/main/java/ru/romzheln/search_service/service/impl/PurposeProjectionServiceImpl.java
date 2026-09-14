package ru.romzheln.search_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.romzheln.search_service.dto.event.PurposeEvent;
import ru.romzheln.search_service.exception.PurposeProjectionNotFoundException;
import ru.romzheln.search_service.model.projection.PurposeProjection;
import ru.romzheln.search_service.repository.PurposeProjectionRepository;
import ru.romzheln.search_service.service.PurposeProjectionService;

@Component
@RequiredArgsConstructor
@Slf4j
public class PurposeProjectionServiceImpl implements PurposeProjectionService {
    
    private final PurposeProjectionRepository repository;
    
    @Override
    @Transactional
    public PurposeProjection create(Long id,
                                    PurposeEvent event) {
    PurposeProjection projection =
        PurposeProjection.builder().id(id).purposeName(event.name()).build();
    repository.save(projection);
    log.info("Проекция цели с ID {} успешно сохранена", id);
        return projection;
    }

    @Override
    @Transactional
    public PurposeProjection update(Long id,
                                    PurposeEvent event) {
        PurposeProjection projection = getProjection(id);
        boolean changed = false;
    if (event.name() != null && !event.name()
            .equals(projection.getPurposeName())){
      projection.setPurposeName(event.name());
      changed = true;
    }
        if (changed) {
            log.info("Проекция цели с ID {} успешно изменена",
                    id);
        } else {
            log.warn("Отсутствуют данные для изменения проекции цели с ID {}",
                    id);
        }
        return projection;
    }

    @Override
    @Transactional(readOnly = true)
    public PurposeProjection findById(Long id) {
        return getProjection(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        PurposeProjection projection = getProjection(id);
        repository.delete(projection);
        log.info("Проекция цели с ID {} успешно удалена", id);
    }

    private PurposeProjection getProjection(Long id){
        return repository.findById(id).orElseThrow(()-> new PurposeProjectionNotFoundException(id));
    }
}
