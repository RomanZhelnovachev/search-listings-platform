package ru.romzheln.listing.model.outbox;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.romzheln.listing.model.enums.EventType;

@Entity
@Table(name = "outbox_events")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OutboxEvent {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "outbox_seq"
    )
    @SequenceGenerator(
            name = "outbox_seq",
            sequenceName = "outbox_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "event_id", nullable = false, unique = true)
    private UUID eventId;

    @Column(name = "listing_id", nullable = false)
    private Long listingId;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    private EventType eventType;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "listing_payload", columnDefinition = "jsonb", nullable = false)
    private JsonNode listingPayload;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "property_payload", columnDefinition = "jsonb", nullable = false)
    private JsonNode propertyPayload;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "processed_at")
    private Instant processedAt;

}
