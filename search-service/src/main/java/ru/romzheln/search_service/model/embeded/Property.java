package ru.romzheln.search_service.model.embeded;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.*;
import ru.romzheln.search_service.model.enums.Own;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Property {

  @Embedded private Location location;

  @Column(name = "square")
  private BigDecimal square;

  @Enumerated(EnumType.STRING)
  @Column(name = "own")
  private Own own;

  @Column(name = "first_owner")
  private Boolean firstOwner;

  @Builder.Default
  @ElementCollection
  @CollectionTable(
      name = "listing_communications",
      joinColumns = {
        @JoinColumn(name = "listing_id", referencedColumnName = "id"),
        @JoinColumn(name = "region", referencedColumnName = "region")
      })
  @Column(name = "communication_id")
  Set<Long> communicationIds = new HashSet<>();
}
