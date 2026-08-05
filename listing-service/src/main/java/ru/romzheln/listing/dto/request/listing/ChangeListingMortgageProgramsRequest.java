package ru.romzheln.listing.dto.request.listing;

import java.util.Set;

public record ChangeListingMortgageProgramsRequest(

        Set<Long> mortgageProgramIds
) {
}
