package ru.romzheln.listing.resolver;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.listing.model.entity.commercial.Purpose;
import ru.romzheln.listing.model.entity.common.AdditionalBuilding;
import ru.romzheln.listing.model.entity.common.Developer;
import ru.romzheln.listing.model.entity.common.LandUse;
import ru.romzheln.listing.model.entity.common.ResidentialComplex;
import ru.romzheln.listing.repository.*;
import ru.romzheln.listing.service.impl.*;

@Component
@RequiredArgsConstructor
public class PropertyReferenceResolver {

    private final DeveloperServiceImpl developerService;
    private final ResidentialComplexServiceImpl complexService;
    private final PurposeServiceImpl purposeService;
    private final LandUseServiceImpl landUseService;
    private final AdditionalBuildingsServiceImpl additionalBuildingsService;

    public Developer getDeveloper(Long id) {
        return developerService.getDeveloper(id);
    }

    public ResidentialComplex getComplex(Long id) {
        return complexService.getComplex(id);
    }

    public Set<Purpose> getAllPurposesById(Set<Long> purposes){
        return purposeService.getAllPurposesBiIds(purposes);
    }

    public LandUse getLandUse(Long id){
        return landUseService.getLandUse(id);
    }

    public Set<AdditionalBuilding> getAllAdditionalBuildingsById(Set<Long> additionalBuildings){
        return additionalBuildingsService.getAllAdditionalBuildingsByIds(additionalBuildings);
    }
}
