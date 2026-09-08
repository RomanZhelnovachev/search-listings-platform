package ru.romzheln.search_service.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.CommunicationEvent;
import ru.romzheln.search_service.dto.event.PurposeEvent;

@Component
public class ProjectionMapper {

    public CommunicationEvent toCommunicationEvent(JsonNode payload){
        String type = payload.get("type").asText();
        return new CommunicationEvent(type);
    }

    public PurposeEvent toPurposeEvent(JsonNode payload){
        String name = payload.get("name").asText();
        return new PurposeEvent(name);
    }
}
