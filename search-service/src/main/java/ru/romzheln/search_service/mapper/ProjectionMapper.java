package ru.romzheln.search_service.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.CommunicationEvent;
import ru.romzheln.search_service.dto.event.PurposeEvent;
import ru.romzheln.search_service.util.MapperUtil;

@Component
public class ProjectionMapper {

    public CommunicationEvent toCommunicationEvent(JsonNode payload){
        return new CommunicationEvent(MapperUtil.text(payload, "type"));
    }

    public PurposeEvent toPurposeEvent(JsonNode payload){
        return new PurposeEvent(MapperUtil.text(payload, "name"));
    }
}
