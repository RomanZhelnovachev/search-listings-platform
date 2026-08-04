package ru.romzheln.listing.dto.common;

import lombok.Builder;

@Builder
public record CommonLandDetailsDto(

        Long landUse,

        String road,

        String fencing
) {
}
