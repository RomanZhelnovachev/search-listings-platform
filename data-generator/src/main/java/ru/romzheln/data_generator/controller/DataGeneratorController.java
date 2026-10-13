package ru.romzheln.data_generator.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.romzheln.data_generator.dto.request.GenerateDataRequest;
import ru.romzheln.data_generator.dto.request.GenerateRequestsRequest;

@RequestMapping("/api/v1/generator/generate")
public interface DataGeneratorController {

    @PostMapping("/data")
    void generateData(@Valid @RequestBody GenerateDataRequest request);

    @PostMapping("/request")
    void generateRequest(@Valid @RequestBody GenerateRequestsRequest request);
}
