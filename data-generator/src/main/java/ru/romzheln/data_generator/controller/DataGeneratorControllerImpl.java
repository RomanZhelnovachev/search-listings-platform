package ru.romzheln.data_generator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.romzheln.data_generator.dto.request.GenerateRequest;
import ru.romzheln.data_generator.service.GeneratorService;

@RestController
@RequiredArgsConstructor
public class DataGeneratorControllerImpl implements DataGeneratorController{

    private final GeneratorService service;

    @Override
    public void generate(GenerateRequest request) {
        service.dataGenerate(request);
    }
}
