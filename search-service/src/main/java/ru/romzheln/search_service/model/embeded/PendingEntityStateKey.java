package ru.romzheln.search_service.model.embeded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import ru.romzheln.search_service.model.enums.EntityType;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class PendingEntityStateKey {

  @Enumerated(EnumType.STRING)
  @Column(name = "entity_type")
  private EntityType entityType;

  @Column(name = "id")
  private Long id;
}
