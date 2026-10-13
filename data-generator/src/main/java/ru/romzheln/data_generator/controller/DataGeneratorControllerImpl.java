package ru.romzheln.data_generator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.romzheln.data_generator.dto.request.GenerateDataRequest;
import ru.romzheln.data_generator.dto.request.GenerateRequestsRequest;
import ru.romzheln.data_generator.service.GeneratorService;

@RestController
@RequiredArgsConstructor
public class DataGeneratorControllerImpl implements DataGeneratorController{

    private final GeneratorService service;

    @Override
    public void generateData(GenerateDataRequest request) {
        service.dataGenerate(request);
    }

    @Override
    public void generateRequest(GenerateRequestsRequest request) {
        service.requestGenerate(request);
    }
}
