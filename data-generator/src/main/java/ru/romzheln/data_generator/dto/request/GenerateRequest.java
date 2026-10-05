package ru.romzheln.data_generator.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.romzheln.data_generator.dto.PercentDto;

public record GenerateRequest(

        @NotNull
        @Positive
        Integer total,

        PercentDto percents

) {}
