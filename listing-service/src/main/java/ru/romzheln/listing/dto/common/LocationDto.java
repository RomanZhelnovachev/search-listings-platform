package ru.romzheln.listing.dto.common;

import lombok.Builder;
import ru.romzheln.listing.model.enums.Region;

@Builder
public record LocationDto(

        Region region,

        String populatedArea,

        String street,

        String house,

        String building,

        String apartment
) {
}
