package ru.romzheln.search_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.romzheln.search_service.dto.event.Message;
import ru.romzheln.search_service.exception.UnknownEventType;
import ru.romzheln.search_service.handler.Handler;
import ru.romzheln.search_service.model.enums.EventType;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventConsumer {

    private final Map<EventType, Handler> handlers;

    @KafkaListener(
            topics = "${spring.kafka.topic.listing-events}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void read(Message message){

        log.info("Получено событие: {}", message);
        getHandler(message.eventType()).handle(message);

    }

    private Handler getHandler(EventType type){
        return Optional.ofNullable(handlers.get(type)).orElseThrow(()-> new UnknownEventType(type));
    }
}
