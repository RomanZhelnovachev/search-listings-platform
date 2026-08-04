package ru.romzheln.listing.dto.event.listing;

import ru.romzheln.listing.dto.event.OutboxPayload;
import ru.romzheln.listing.model.entity.listing.Image;

import java.util.Set;

public record ImageRemovedEvent(

        Set<Image> images
) implements OutboxPayload {
}
