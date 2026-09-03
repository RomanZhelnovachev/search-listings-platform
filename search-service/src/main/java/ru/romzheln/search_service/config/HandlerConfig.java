package ru.romzheln.search_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.romzheln.search_service.handler.*;
import ru.romzheln.search_service.model.enums.AggregateType;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class HandlerConfig {

    private final CommunicationHandler communicationHandler;
    private final ListingHandler listingHandler;
    private final PurposeHandler purposeHandler;
    private final DeveloperHandler developerHandler;
    private final LandUseHandler landUseHandler;
    private final PropertyHandler propertyHandler;
    private final ResidentialComplexHandler residentialComplexHandler;

    @Bean
    public Map<AggregateType, Handler> handlers(){
        return Map.of(
                AggregateType.COMMUNICATION, communicationHandler,
                AggregateType.DEVELOPER, developerHandler,
                AggregateType.LAND_USE, landUseHandler,
                AggregateType.LISTING, listingHandler,
                AggregateType.PROPERTY, propertyHandler,
                AggregateType.PURPOSE, purposeHandler,
                AggregateType.RESIDENTIAL_COMPLEX, residentialComplexHandler
        );
    }
}
