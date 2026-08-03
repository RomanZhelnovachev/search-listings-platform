package ru.romzheln.listing.dto.request.property.common;

public record CommonLandDetailsRequest(

        Long landUse,

        String road,

        String fencing
) {
}
