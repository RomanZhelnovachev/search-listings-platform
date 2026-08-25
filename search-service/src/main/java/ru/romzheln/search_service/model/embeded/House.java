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
            name = "property_additional_building",
            joinColumns = @JoinColumn(
                    name = "property_id",
                    referencedColumnName = "property_id")
    )
    @Builder.Default
    private Set<AdditionalBuilding> additionalBuildings = new HashSet<>();

    @Column(name = "land_plot_square")
    private BigDecimal landPlotSquare;
}
