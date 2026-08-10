package ru.romzheln.listing.resolver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.listing.model.entity.commercial.Purpose;
import ru.romzheln.listing.model.entity.common.AdditionalBuilding;
import ru.romzheln.listing.model.entity.common.Developer;
import ru.romzheln.listing.model.entity.common.LandUse;
import ru.romzheln.listing.model.entity.common.ResidentialComplex;
import ru.romzheln.listing.repository.*;
import ru.romzheln.listing.service.impl.*;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class PropertyReferenceResolver {

    private final DeveloperServiceImpl developerService;
    private final ResidentialComplexServiceImpl complexService;
    private final PurposeServiceImpl purposeService;
    private final LandUseServiceImpl landUseService;
    private final AdditionalBuildingsServiceImpl additionalBuildingsService;

    public Developer getDeveloper(Long id) {
        return developerService.get(id);
    }

    public ResidentialComplex getComplex(Long id) {
        return complexService.get(id);
    }

    public Set<Purpose> getAllPurposesById(Set<Long> purposes){
        return purposeService.getAllPurposesBiIds(purposes);
    }

    public LandUse getLandUse(Long id){
        return landUseService.get(id);
    }

    public Set<AdditionalBuilding> getAllAdditionalBuildingsById(Set<Long> additionalBuildings){
        return additionalBuildingsService.getAllAdditionalBuildingsByIds(additionalBuildings);
    }
}
