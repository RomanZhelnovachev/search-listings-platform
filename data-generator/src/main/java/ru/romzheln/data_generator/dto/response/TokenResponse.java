package ru.romzheln.data_generator.dto.response;

public record TokenResponse(

        String accessToken,

        String tokenType,

        Long expiresIn
) {}
