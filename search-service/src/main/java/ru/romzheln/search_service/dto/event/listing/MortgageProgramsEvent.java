package ru.romzheln.search_service.dto.event.listing;

import java.util.Set;

public record MortgageProgramsEvent(Set<Long> mortgagePrograms) {}
