package ru.romzheln.search_service.model.embeded;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LandPlot {

    @Embedded
    private CommonLandDetails commonLandDetails;

    @ElementCollection
    @CollectionTable(
            name = "property_additional_building",
            joinColumns = @JoinColumn(
                    name = "property_id",
                    referencedColumnName = "property_id")
    )
    @Builder.Default
    private Set<AdditionalBuilding> additionalBuildings = new HashSet<>();
}
