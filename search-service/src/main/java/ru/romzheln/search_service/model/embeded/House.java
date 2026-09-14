package ru.romzheln.search_service.model.embeded;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class House {

    @Embedded
    private CommonPhysicalDetails commonPhysicalDetails;

    @Embedded
    private CommonLandDetails commonLandDetails;

    @Embedded private Developer developer;

    @Embedded private ResidentialComplex complex;

    @Column(name = "construction_stage")
    private String constructionStage;

    @ElementCollection
    @CollectionTable(
            name = "listing_additional_buildings",
            joinColumns = {
                    @JoinColumn(name = "listing_id", referencedColumnName = "id"),
                    @JoinColumn(name = "region", referencedColumnName = "region")
            }
    )
    @Column(name = "additional_building_id")
    @Builder.Default
    private Set<Long> additionalBuildings = new HashSet<>();

    @Column(name = "land_plot_square")
    private BigDecimal landPlotSquare;
}
