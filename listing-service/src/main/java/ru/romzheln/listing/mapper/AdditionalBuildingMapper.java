package ru.romzheln.listing.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import ru.romzheln.listing.dto.event.AdditionalBuildingEvent;
import ru.romzheln.listing.dto.response.AdditionalBuildingResponse;
import ru.romzheln.listing.model.entity.common.AdditionalBuilding;

import java.util.List;

@Component
public class AdditionalBuildingMapper implements ReferenceMapper<AdditionalBuilding, AdditionalBuildingResponse, AdditionalBuildingEvent> {

    public AdditionalBuildingResponse toResponse(AdditionalBuilding building){
        return AdditionalBuildingResponse.builder()
                .name(building.getName())
                .description(building.getDescription())
                .build();
    }

    @Override
    public Page<AdditionalBuildingResponse> toPageResponse(Page<AdditionalBuilding> additionalBuildings) {
        List<AdditionalBuildingResponse> responses = additionalBuildings.getContent()
                .stream()
                .map(this::toResponse)
                .toList();
        return new PageImpl<>(responses, additionalBuildings.getPageable(), additionalBuildings.getTotalElements());
    }

    @Override
    public AdditionalBuildingEvent toEvent(AdditionalBuilding building) {
        return new AdditionalBuildingEvent(building.getName(),
                building.getDescription());
    }
}
