package ru.romzheln.search_service.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.listing.*;
import ru.romzheln.search_service.util.MapperUtil;

@Component
public class ListingMapper {

    public ListingCreatedEvent toListingCreatedEvent(JsonNode payload){
        return ListingCreatedEvent.builder()
                .title(MapperUtil.text(payload, "title"))
                .description(MapperUtil.text(payload, "description"))
                .ownerId(MapperUtil.toLong(payload, "ownerId"))
                .propertyId(MapperUtil.toLong(payload, "propertyId"))
                .dealType(MapperUtil.text(payload, "dealType"))
                .price(MapperUtil.decimal(payload, "price"))
                .build();
    }

    public ListingUpdatedEvent toListingUpdatedEvent(JsonNode payload){
        return ListingUpdatedEvent.builder()
                .title(MapperUtil.text(payload, "title"))
                .description(MapperUtil.text(payload, "description"))
                .dealType(MapperUtil.text(payload, "dealType"))
                .build();
    }

    public ChangePriceEvent toChangePriceEvent(JsonNode payload){
        return new ChangePriceEvent(MapperUtil.decimal(payload,"oldPrice"), MapperUtil.decimal(payload, "newPrice"));
    }

    public PromotionEvent toPromotionEvent(JsonNode payload){
        return new PromotionEvent(MapperUtil.toLong(payload, "promotionId"));
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
