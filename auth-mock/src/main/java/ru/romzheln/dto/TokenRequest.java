package ru.romzheln.dto;

import ru.romzheln.security.Role;

public record TokenRequest(

        String name,

        Role role
) {
}
