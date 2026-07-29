package ru.romzheln.listing.mapper;

import org.springframework.stereotype.Component;
import ru.romzheln.listing.dto.event.listing.ListingCreatedEvent;
import ru.romzheln.listing.dto.event.listing.ListingUpdatedEvent;
import ru.romzheln.listing.dto.response.ListingResponse;
import ru.romzheln.listing.model.entity.listing.Listing;

@Component
public class ListingMapper {

    public ListingResponse toResponse(Listing listing){
        return ListingResponse.builder()
                .id(listing.getId())
                .title(listing.getTitle())
                .description(listing.getDescription())
                .status(listing.getStatus())
                .ownerId(listing.getOwner().getId())
                .propertyId(listing.getProperty().getId())
                .dealType(listing.getDealType())
                .price(listing.getPrice())
                .build();
    }

    public ListingCreatedEvent toListingCreatedEvent(Listing listing){
        return ListingCreatedEvent.builder()
                .title(listing.getTitle())
                .description(listing.getDescription())
                .ownerId(listing.getOwner().getId())
                .propertyId(listing.getProperty().getId())
                .dealType(listing.getDealType())
                .price(listing.getPrice())
                .build();
    }

    public ListingUpdatedEvent toListingUpdatedEvent(Listing listing){
        return new ListingUpdatedEvent(listing.getTitle(),
                listing.getDescription(), listing.getDealType());
    }
}
