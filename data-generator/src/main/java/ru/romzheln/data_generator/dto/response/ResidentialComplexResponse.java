package ru.romzheln.data_generator.dto.response;

import jakarta.validation.constraints.NotBlank;

public record ResidentialComplexResponse(@NotBlank String name) {}
