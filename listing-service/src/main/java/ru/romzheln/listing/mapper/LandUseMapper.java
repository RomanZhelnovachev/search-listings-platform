package ru.romzheln.listing.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import ru.romzheln.listing.dto.event.LandUseEvent;
import ru.romzheln.listing.dto.response.LandUseResponse;
import ru.romzheln.listing.model.entity.common.LandUse;

import java.util.List;

@Component
public class LandUseMapper implements ReferenceMapper<LandUse, LandUseResponse, LandUseEvent>{

    @Override
    public LandUseResponse toResponse(LandUse landUse) {
        return LandUseResponse.builder()
                .name(landUse.getName())
                .description(landUse.getDescription())
                .build();
    }

    @Override
    public Page<LandUseResponse> toPageResponse(Page<LandUse> landUses) {
        List<LandUseResponse> responses = landUses.getContent()
                .stream()
                .map(this::toResponse)
                .toList();
        return new PageImpl<>(responses, landUses.getPageable(), landUses.getTotalElements());
    }

    @Override
    public LandUseEvent toEvent(LandUse landUse) {
        return new LandUseEvent(landUse.getName(), landUse.getDescription());
    }
}
