package ru.romzheln.listing.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import ru.romzheln.listing.dto.event.PurposeEvent;
import ru.romzheln.listing.dto.response.PurposeResponse;
import ru.romzheln.listing.model.entity.commercial.Purpose;

import java.util.List;

@Component
public class PurposeMapper implements ReferenceMapper<Purpose, PurposeResponse, PurposeEvent>{

    @Override
    public PurposeResponse toResponse(Purpose purpose) {
        return PurposeResponse.builder()
                .name(purpose.getName())
                .description(purpose.getDescription())
                .build();
    }

    @Override
    public Page<PurposeResponse> toPageResponse(Page<Purpose> purposes) {
        List<PurposeResponse> content = purposes.getContent()
                .stream()
                .map(this::toResponse)
                .toList();
        return new PageImpl<>(content, purposes.getPageable(), purposes.getTotalElements());
    }

    @Override
    public PurposeEvent toEvent(Purpose purpose) {
        return PurposeEvent.builder()
                .name(purpose.getName())
                .description(purpose.getDescription())
                .build();
    }
}
