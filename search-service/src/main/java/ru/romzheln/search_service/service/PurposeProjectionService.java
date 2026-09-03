package ru.romzheln.search_service.service;

import ru.romzheln.search_service.dto.event.PurposeEvent;
import ru.romzheln.search_service.model.projection.PurposeProjection;

public interface PurposeProjectionService {

    PurposeProjection create(Long id, PurposeEvent event);

    PurposeProjection update(Long id, PurposeEvent event);

    PurposeProjection findById(Long id);

    void delete(Long id);
}
