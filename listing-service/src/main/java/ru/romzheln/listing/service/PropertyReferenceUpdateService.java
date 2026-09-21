package ru.romzheln.listing.service;

import ru.romzheln.listing.dto.event.DeveloperEvent;
import ru.romzheln.listing.dto.event.LandUseEvent;
import ru.romzheln.listing.dto.event.ResidentialComplexEvent;

public interface PropertyReferenceUpdateService {

    void updateDeveloper(Long developerId, DeveloperEvent event);

    void updateLandUse(Long landUseId, LandUseEvent event);

    void updateResidentialComplex(Long complexId, ResidentialComplexEvent event);
}
