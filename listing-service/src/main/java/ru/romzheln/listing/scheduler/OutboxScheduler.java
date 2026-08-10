package ru.romzheln.listing.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.romzheln.listing.service.EventPublisher;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxScheduler {

    private final EventPublisher publisher;

    @Scheduled(fixedDelayString = "${outbox.scheduler.delay}")
    public void publishEvents(){
        log.info("Запущен процесс публикации событий");
        publisher.publish();
    }
}
