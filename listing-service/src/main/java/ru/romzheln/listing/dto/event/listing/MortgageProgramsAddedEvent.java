package ru.romzheln.listing.dto.event.listing;

import java.util.Set;
import ru.romzheln.listing.dto.event.OutboxPayload;

public record MortgageProgramsAddedEvent(Set<Long> mortgagePrograms) implements OutboxPayload {}
