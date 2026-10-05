package ru.romzheln.data_generator.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.romzheln.data_generator.dto.request.GenerateRequest;

@RequestMapping("/api/v1/generator")
public interface DataGeneratorController {

    @PostMapping("/generate")
    void generate(@Valid @RequestBody GenerateRequest request);
}
