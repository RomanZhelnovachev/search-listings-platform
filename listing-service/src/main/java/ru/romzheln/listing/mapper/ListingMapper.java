package ru.romzheln.listing.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import ru.romzheln.listing.dto.event.listing.ListingCreatedEvent;
import ru.romzheln.listing.dto.event.listing.ListingUpdatedEvent;
import ru.romzheln.listing.dto.response.ListingResponse;
import ru.romzheln.listing.model.entity.listing.Listing;

import java.util.List;

@Component
public class ListingMapper {

    public ListingResponse toResponse(Listing listing){
        return ListingResponse.builder()
                .id(listing.getId())
                .title(listing.getTitle())
                .description(listing.getDescription())
                .status(listing.getStatus())
                .ownerId(listing.getOwnerId())
                .propertyId(listing.getProperty().getId())
                .dealType(listing.getDealType())
                .price(listing.getPrice())
                .build();
    }

    public ListingCreatedEvent toListingCreatedEvent(Listing listing){
        return ListingCreatedEvent.builder()
                .title(listing.getTitle())
                .description(listing.getDescription())
                .ownerId(listing.getOwnerId())
                .propertyId(listing.getProperty().getId())
                .dealType(listing.getDealType())
                .price(listing.getPrice())
                .build();
    }

    public ListingUpdatedEvent toListingUpdatedEvent(Listing listing){
        return new ListingUpdatedEvent(listing.getTitle(),
                listing.getDescription(), listing.getDealType());
    }

    public Page<ListingResponse> toPageResponse(Page<Listing> listings){
        List<ListingResponse> responses = listings.getContent()
                .stream()
                .map(this::toResponse)
                .toList();
        return new PageImpl<>(responses, listings.getPageable(),
                listings.getTotalElements());
    }
}
