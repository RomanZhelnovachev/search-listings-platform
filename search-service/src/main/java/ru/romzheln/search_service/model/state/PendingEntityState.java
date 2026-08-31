package ru.romzheln.search_service.model.state;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.romzheln.search_service.model.embeded.PendingEntityStateKey;

@Entity
@Table(name = "pending_entity_state")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PendingEntityState {

    @EmbeddedId
    private PendingEntityStateKey key;

    @Column(name = "version", nullable = false)
    private Long version;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload", columnDefinition = "jsonb", nullable = false)
    private JsonNode payload;
}
