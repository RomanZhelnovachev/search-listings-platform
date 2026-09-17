package ru.romzheln.search_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.romzheln.search_service.model.enums.PropertyType;
import ru.romzheln.search_service.strategy.*;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class StrategyConfig {

    private final ApartmentStrategy apartmentStrategy;
    private final CommercialStrategy commercialStrategy;
    private final HouseStrategy houseStrategy;
    private final LandPlotStrategy landPlotStrategy;

    @Bean
    public Map<PropertyType, Strategy<?>> strategies(){
        return Map.of(
                PropertyType.APARTMENT, apartmentStrategy,
                PropertyType.COMMERCIAL, commercialStrategy,
                PropertyType.HOUSE, houseStrategy,
                PropertyType.LAND_PLOT, landPlotStrategy
        );
    }
}
