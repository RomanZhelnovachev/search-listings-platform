package ru.romzheln.search_service.service;

import ru.romzheln.search_service.dto.event.CommunicationEvent;
import ru.romzheln.search_service.model.projection.CommunicationProjection;

public interface CommunicationProjectionService {

    CommunicationProjection create(Long id, CommunicationEvent event);

    CommunicationProjection update(Long id, CommunicationEvent event);

    CommunicationProjection findById(Long id);

    void delete(Long id);
}
