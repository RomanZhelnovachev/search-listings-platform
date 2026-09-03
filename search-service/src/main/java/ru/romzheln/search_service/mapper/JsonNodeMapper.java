package ru.romzheln.search_service.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.CommunicationEvent;
import ru.romzheln.search_service.dto.event.DeveloperEvent;
import ru.romzheln.search_service.dto.event.PurposeEvent;

@Component
public class JsonNodeMapper {

    public CommunicationEvent toCommunicationEvent(JsonNode payload){
        String type = payload.get("type").asText();
        String description = payload.get("description").asText();
        return new CommunicationEvent(type, description);
    }

    public PurposeEvent toPurposeEvent(JsonNode payload){
        String name = payload.get("name").asText();
        String description = payload.get("description").asText();
        return new PurposeEvent(name, description);
    }

    public DeveloperEvent toDeveloperEvent(JsonNode payload){
        String name = payload.get("name").asText();
        return new DeveloperEvent(name);
    }
}
