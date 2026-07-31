package ru.romzheln.listing.dto.request.property;

import ru.romzheln.listing.model.enums.Region;

public record LocationRequest(

        Region region,

        String populatedArea,

        String street,

        String house,

        String building,

        String apartment
) {
}
