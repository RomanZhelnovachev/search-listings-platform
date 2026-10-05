package ru.romzheln.data_generator.dto.response;

import jakarta.validation.constraints.NotBlank;

public record DeveloperResponse(@NotBlank String name) {}
