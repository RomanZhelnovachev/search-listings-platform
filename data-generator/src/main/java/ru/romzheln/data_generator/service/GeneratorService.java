package ru.romzheln.data_generator.service;

import ru.romzheln.data_generator.dto.request.GenerateRequest;

public interface GeneratorService {

void dataGenerate(GenerateRequest request);
}
