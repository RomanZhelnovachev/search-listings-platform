package ru.romzheln.search_service.model.embeded;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "developer_id")
    private Long developerId;

    @Embedded
    private Developer developer;

    @Column(name = "complex_id")
    private Long complexId;

    @Embedded
    private ResidentialComplex complex;

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
