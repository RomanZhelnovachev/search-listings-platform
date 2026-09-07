package ru.romzheln.search_service.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.Message;
import ru.romzheln.search_service.mapper.JsonNodeMapper;
import ru.romzheln.search_service.service.ListingReadModelService;

@Component
@RequiredArgsConstructor
public class ListingHandler implements Handler{

    private final ListingReadModelService service;
    private final JsonNodeMapper mapper;

    @Override
    public void handle(Message message) {
        Long listingId = message.aggregateId();
        switch (message.eventType()){
            case CREATED -> service.createReadModel(listingId, mapper.toListingCreatedEvent(message.listingPayload()), mapper.toPropertyEvent(message.propertyPayload()));
        }
    }
}
