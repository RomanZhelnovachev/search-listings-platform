package ru.romzheln.listing.exception;

public record AppError(

        int statusCode,

        String message
) {
}
