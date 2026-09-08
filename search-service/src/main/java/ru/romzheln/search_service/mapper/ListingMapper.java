package ru.romzheln.search_service.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.listing.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Component
public class ListingMapper {

    public ListingCreatedEvent toListingCreatedEvent(JsonNode payload){
        return ListingCreatedEvent.builder()
                .title(payload.get("title").asText())
                .description(payload.get("description").asText())
                .ownerId(payload.get("ownerId").asLong())
                .propertyId(payload.get("propertyId").asLong())
                .propertyType(payload.get("propertyType").asText())
                .dealType(payload.get("dealType").asText())
                .price(new BigDecimal(payload.get("price").asText()))
                .build();
    }

    public ListingUpdatedEvent toListingUpdatedEvent(JsonNode payload){
        return ListingUpdatedEvent.builder()
                .title(payload.get("title").asText())
                .description(payload.get("description").asText())
                .dealType(payload.get("dealType").asText())
                .build();
    }

    public ChangePriceEvent toChangePriceEvent(JsonNode payload){
        return new ChangePriceEvent(new BigDecimal(payload.get("oldPrice").asText()), new BigDecimal(payload.get("newPrice").asText()));
    }

    public PromotionEvent toPromotionEvent(JsonNode payload){
        return new PromotionEvent(payload.get("promotionId").asLong());
    }

    public MortgageProgramsEvent toMortgageProgramsEvent(JsonNode payload){
        return new MortgageProgramsEvent(getSetLong(payload.get("mortgagePrograms")));
    }

    public ImageEvent toImageEvent(JsonNode payload){
        return new ImageEvent(getSetLong(payload.get("images")));
    }

    private Set<Long> getSetLong(JsonNode payload) {
        Set<Long> ids = new HashSet<>();
        if(payload != null && payload.isArray()){
            for(JsonNode node : payload){
                ids.add(node.asLong());
            }
        }
        return ids;
    }
}
