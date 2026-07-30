package ru.romzheln.listing.dto.event.listing;

import ru.romzheln.listing.model.entity.listing.MortgageProgram;

import java.util.Set;

public record MotgageProgramRemovedEvent(

        Set<MortgageProgram> mortgagePrograms
) {
}
