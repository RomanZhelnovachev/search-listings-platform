package ru.romzheln.listing.dto.event.listing;

import ru.romzheln.listing.dto.event.OutboxPayload;
import ru.romzheln.listing.model.entity.listing.MortgageProgram;

import java.util.Set;

public record MotgageProgramRemovedEvent(

        Set<Long> mortgagePrograms
) implements OutboxPayload {
}
