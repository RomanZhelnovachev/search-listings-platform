package ru.romzheln.search_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.romzheln.search_service.handler.*;
import ru.romzheln.search_service.model.enums.EventType;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class HandlerConfig {

    private final CommunicationHandler communicationHandler;
    private final ListingHandler listingHandler;
    private final PurposeHandler purposeHandler;

    @Bean
    public Map<EventType, Handler> handlers(){
        return Map.ofEntries(
                Map.entry(EventType.CREATED, listingHandler),
                Map.entry(EventType.UPDATED, listingHandler),
                Map.entry(EventType.REMOVED, listingHandler),
                Map.entry(EventType.PRICE_CHANGED, listingHandler),
                Map.entry(EventType.UPDATED_PROPERTY, listingHandler),
                Map.entry(EventType.PROMOTION_ADDED, listingHandler),
                Map.entry(EventType.PROMOTION_DISABLED, listingHandler),
                Map.entry(EventType.MORTGAGE_PROGRAM_ADDED, listingHandler),
                Map.entry(EventType.MORTGAGE_PROGRAMS_REMOVED, listingHandler),
                Map.entry(EventType.PUBLISHED, listingHandler),
                Map.entry(EventType.ARCHIVED, listingHandler),
                Map.entry(EventType.APPROVED, listingHandler),
                Map.entry(EventType.IMAGES_ADDED, listingHandler),
                Map.entry(EventType.IMAGES_REMOVED, listingHandler),
                Map.entry(EventType.UPDATED_DEVELOPER, listingHandler),
                Map.entry(EventType.UPDATED_LAND_USE, listingHandler),
                Map.entry(EventType.UPDATED_COMPLEX, listingHandler),
                Map.entry(EventType.CREATED_COMMUNICATION, communicationHandler),
                Map.entry(EventType.UPDATED_COMMUNICATION, communicationHandler),
                Map.entry(EventType.CREATED_PURPOSE, purposeHandler),
                Map.entry(EventType.UPDATED_PURPOSE, purposeHandler)
        );
    }
}
