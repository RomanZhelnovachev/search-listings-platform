package ru.romzheln.listing.resolver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.listing.exception.DeveloperNotFoundException;
import ru.romzheln.listing.exception.LandUseNotFoundException;
import ru.romzheln.listing.exception.ResidentialComplexNotFoundException;
import ru.romzheln.listing.model.entity.commercial.Purpose;
import ru.romzheln.listing.model.entity.common.AdditionalBuilding;
import ru.romzheln.listing.model.entity.common.Developer;
import ru.romzheln.listing.model.entity.common.LandUse;
import ru.romzheln.listing.model.entity.common.ResidentialComplex;
import ru.romzheln.listing.repository.*;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PropertyReferenceResolver {

    private final DeveloperRepository developerRepository;
    private final ResidentialComplexRepository complexRepository;
    private final PurposeRepository purposeRepository;
    private final LandUseRepository landUseRepository;
    private final AdditionalBuildingRepository additionalBuildingRepository;

    public Developer getDeveloper(Long id) {
        return developerRepository.findById(id)
                .orElseThrow(() -> new DeveloperNotFoundException(id));
    }

    public ResidentialComplex getComplex(Long id) {
        return complexRepository.findById(id)
                .orElseThrow(() -> new ResidentialComplexNotFoundException(id));
    }

    public Set<Purpose> getAllPurposesById(Set<Long> purposes){
        return new HashSet<>(purposeRepository.findAllById(purposes));
    }

    public LandUse getLandUse(Long id){
        return landUseRepository.findById(id).orElseThrow(()-> new LandUseNotFoundException(id));
    }

    public Set<AdditionalBuilding> getAllAdditionalBuildingsById(Set<Long> additionalBuildings){
        return new HashSet<>(additionalBuildingRepository.findAllById(additionalBuildings));
    }
}
