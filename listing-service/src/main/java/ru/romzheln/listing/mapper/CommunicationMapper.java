package ru.romzheln.listing.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import ru.romzheln.listing.dto.event.CommunicationEvent;
import ru.romzheln.listing.dto.response.CommunicationResponse;
import ru.romzheln.listing.model.entity.common.Communication;

import java.util.List;

@Component
public class CommunicationMapper implements ReferenceMapper<Communication, CommunicationResponse, CommunicationEvent>{

    @Override
    public CommunicationResponse toResponse(Communication communication) {
        return CommunicationResponse.builder()
                .type(communication.getCommunicationType())
                .description(communication.getDescription())
                .build();
    }

    @Override
    public Page<CommunicationResponse> toPageResponse(Page<Communication> communications) {
        List<CommunicationResponse> responses = communications.getContent()
                .stream()
                .map(this::toResponse)
                .toList();
        return new PageImpl<>(responses, communications.getPageable(), communications.getTotalElements());
    }

    @Override
    public CommunicationEvent toEvent(Communication communication) {
        return CommunicationEvent.builder()
                .type(communication.getCommunicationType())
                .description(communication.getDescription())
                .build();
    }
}
