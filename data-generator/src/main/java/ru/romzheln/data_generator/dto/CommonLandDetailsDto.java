package ru.romzheln.data_generator.dto;

import lombok.Builder;

@Builder
public record CommonLandDetailsDto(
        Long landUse,
        String landUseName,
        String road,
        String fencing) {}
