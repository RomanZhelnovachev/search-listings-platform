package ru.romzheln.data_generator.dto;

import jakarta.validation.constraints.PositiveOrZero;
import ru.romzheln.data_generator.validator.ValidPercent;

@ValidPercent
public record PercentDto(

        @PositiveOrZero
        Integer apartmentPercent,

        @PositiveOrZero
        Integer commercialPercent,

        @PositiveOrZero
        Integer housePercent,

        @PositiveOrZero
        Integer landPlotPercent
) {}
