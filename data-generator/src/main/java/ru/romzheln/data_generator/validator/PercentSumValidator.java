package ru.romzheln.data_generator.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.romzheln.data_generator.dto.request.GenerateRequest;

import java.util.Objects;
import java.util.stream.Stream;

public class PercentSumValidator implements ConstraintValidator<ValidPercent, GenerateRequest> {
    @Override
    public boolean isValid(GenerateRequest request,
                           ConstraintValidatorContext context) {
        if (request == null) {
            return true;
        }
        int sum = Stream.of(
                        request.percents().apartmentPercent(),
                        request.percents().commercialPercent(),
                        request.percents().housePercent(),
                        request.percents().landPlotPercent()
                )
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();
        return sum == 100;
    }
}
