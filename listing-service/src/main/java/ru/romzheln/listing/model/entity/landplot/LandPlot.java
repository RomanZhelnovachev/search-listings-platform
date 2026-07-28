package ru.romzheln.listing.model.entity.landplot;

import jakarta.persistence.*;
import lombok.*;
import ru.romzheln.listing.model.entity.common.AdditionalBuilding;
import ru.romzheln.listing.model.entity.common.CommonLandDetails;
import ru.romzheln.listing.model.entity.property.Property;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "land_plots")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LandPlot {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Property property;

    @Embedded
    private CommonLandDetails commonLandDetails;

    @OneToMany(mappedBy = "landPlot")
    @Builder.Default
    private Set<AdditionalBuilding> additionalBuildings = new HashSet<>();
}
