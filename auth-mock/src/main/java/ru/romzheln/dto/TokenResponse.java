package ru.romzheln.dto;

public record TokenResponse(

        String accessToken,
        String tokenType,
        Long expiresIn
) {
}
