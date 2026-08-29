package ru.romzheln.listing.dto.event.listing;

import java.util.Set;
import ru.romzheln.listing.dto.event.OutboxPayload;

public record MotgageProgramRemovedEvent(Set<Long> mortgagePrograms) implements OutboxPayload {}
