package ru.romzheln.data_generator.service;

import ru.romzheln.data_generator.dto.request.GenerateRequest;

public interface GeneratorService {

boolean generate(GenerateRequest request);
}
