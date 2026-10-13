package ru.romzheln.data_generator.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record GenerateRequestsRequest(

        @NotNull
        @Positive
        Integer count
) {}
