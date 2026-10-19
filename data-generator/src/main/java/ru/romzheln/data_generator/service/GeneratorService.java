package ru.romzheln.data_generator.service;

import ru.romzheln.data_generator.dto.request.GenerateDataRequest;
import ru.romzheln.data_generator.dto.request.GenerateRequestsRequest;

public interface GeneratorService {

void dataGenerate(GenerateDataRequest request);

void requestGenerate(GenerateRequestsRequest request);

void cleanupDatabases();
}
