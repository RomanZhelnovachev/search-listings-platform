package ru.romzheln.listing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romzheln.listing.model.outbox.OutboxEvent;

public interface OutboxRepository extends JpaRepository<OutboxEvent, Long> {
}
