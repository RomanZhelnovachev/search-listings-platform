package ru.romzheln.search_service.model.embeded;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LandPlot {

  @Embedded private CommonLandDetails commonLandDetails;

  @ElementCollection
  @CollectionTable(
      name = "listing_additional_buildings",
      joinColumns = {
        @JoinColumn(name = "listing_id", referencedColumnName = "id"),
        @JoinColumn(name = "region", referencedColumnName = "region")
      })
  @Column(name = "additional_building_id")
  @Builder.Default
  private Set<Long> additionalBuildings = new HashSet<>();
}
