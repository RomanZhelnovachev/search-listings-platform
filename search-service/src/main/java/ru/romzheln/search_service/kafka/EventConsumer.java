package ru.romzheln.search_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.romzheln.search_service.dto.event.EventMessage;
import ru.romzheln.search_service.exception.UnknownAggregateType;
import ru.romzheln.search_service.handler.Handler;
import ru.romzheln.search_service.model.enums.AggregateType;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventConsumer {

    private final Map<AggregateType, Handler> handlers;

    @KafkaListener(
            topics = "${spring.kafka.topic.listing-events}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void read(EventMessage message){

        log.info("Получено событие: {}", message);
        getHandler(message.aggregateType()).handle(message);

    }

    private Handler getHandler(AggregateType type){
        return Optional.ofNullable(handlers.get(type)).orElseThrow(()-> new UnknownAggregateType(type));
    }
}
