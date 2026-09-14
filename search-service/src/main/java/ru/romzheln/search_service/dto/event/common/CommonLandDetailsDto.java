package ru.romzheln.search_service.dto.event.common;

import lombok.Builder;

@Builder
public record CommonLandDetailsDto(
        Long landUse,
        String landUseName,
        String road,
        String fencing) {}
