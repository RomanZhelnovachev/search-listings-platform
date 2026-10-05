package ru.romzheln.data_generator.dto;

import lombok.Builder;
import ru.romzheln.data_generator.enums.Region;

@Builder
public record LocationDto(
    Region region,
    String populatedArea,
    String street,
    String house,
    String building,
    String apartment) {}
