package ru.romzheln.listing.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.romzheln.listing.model.enums.PropertyType;
import ru.romzheln.listing.service.strategy.PropertyStrategy;
import ru.romzheln.listing.service.strategy.impl.ApartmentStrategy;
import ru.romzheln.listing.service.strategy.impl.CommercialStrategy;
import ru.romzheln.listing.service.strategy.impl.HouseStrategy;
import ru.romzheln.listing.service.strategy.impl.LandPlotStrategy;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class PropertyStrategyConfig {

    private final ApartmentStrategy apartmentStrategy;
    private final CommercialStrategy commercialStrategy;
    private final HouseStrategy houseStrategy;
    private final LandPlotStrategy landPlotStrategy;

    @Bean
    public Map<PropertyType, PropertyStrategy> propertyStrategies(){
        return Map.of(
                PropertyType.APARTMENT, apartmentStrategy,
                PropertyType.HOUSE, houseStrategy,
                PropertyType.COMMERCIAL, commercialStrategy,
                PropertyType.LAND_PLOT, landPlotStrategy
        );
    }
}
