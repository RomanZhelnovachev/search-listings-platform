package ru.romzheln.search_service.model.read_model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.romzheln.search_service.model.embeded.Listing;
import ru.romzheln.search_service.model.embeded.ListingKey;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class ReadModel {

    @EmbeddedId
    private ListingKey key;

    @Embedded
    private Listing listing;

    @Column(name = "is_included_in_search", nullable = false)
    private boolean isIncludedInSearch;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;
}
