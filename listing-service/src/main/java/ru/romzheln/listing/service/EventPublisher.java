package ru.romzheln.listing.service;

import ru.romzheln.listing.model.outbox.OutboxEvent;

import java.util.List;

public interface EventPublisher {

    void publish();
}
