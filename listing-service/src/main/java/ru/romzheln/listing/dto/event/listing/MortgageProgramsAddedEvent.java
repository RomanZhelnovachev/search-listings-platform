package ru.romzheln.listing.dto.event.listing;

import java.util.Set;

public record MortgageProgramsAddedEvent(

        Set<Long> mortgageProgramIds
) {
}
