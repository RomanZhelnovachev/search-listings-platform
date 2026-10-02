package ru.romzheln.data_generator.dto.response;


import ru.romzheln.data_generator.enums.CommunicationType;

public record CommunicationResponse(CommunicationType type, String description) {}
