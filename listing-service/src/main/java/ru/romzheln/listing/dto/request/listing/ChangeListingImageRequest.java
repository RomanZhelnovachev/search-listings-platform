package ru.romzheln.listing.dto.request.listing;

import java.util.Set;

public record ChangeListingImageRequest(

        Set<Long> imageIds
) {
}
