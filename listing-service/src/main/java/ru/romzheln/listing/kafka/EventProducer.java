package ru.romzheln.listing.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.romzheln.listing.dto.kafka.EventMessage;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventProducer {

    private final KafkaTemplate<String, EventMessage> template;

    @Value("${spring.kafka.topic.listing-events}")
    private String topic;

    public CompletableFuture<SendResult<String, EventMessage>> send(EventMessage message){
        return template.send(topic, message.aggregateId().toString(), message)
                .whenComplete((result, exception) -> {
                    if(exception != null){
                        log.error("Ошибка при отправки события {}", message.eventId(), exception);
                        return;
                    }
                    log.info("Событие {} успешно отправлено в Kafka", message.eventId());
                });
    }
}
