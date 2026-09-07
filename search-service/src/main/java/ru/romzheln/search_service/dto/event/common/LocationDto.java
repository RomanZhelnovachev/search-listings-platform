package ru.romzheln.search_service.dto.event.common;

import lombok.Builder;

@Builder
public record LocationDto(
    String region,
    String populatedArea,
    String street,
    String house,
    String building,
    String apartment) {}
