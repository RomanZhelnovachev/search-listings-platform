package ru.romzheln.data_generator.dto.request;

import ru.romzheln.data_generator.enums.Role;

public record TokenRequest(

        String name,

        Role role
) {}
