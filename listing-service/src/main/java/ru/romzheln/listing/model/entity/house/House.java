package ru.romzheln.listing.model.entity.house;

import jakarta.persistence.*;
import lombok.*;
import ru.romzheln.listing.model.entity.common.AdditionalBuilding;
import ru.romzheln.listing.model.entity.common.CommonLandDetails;
import ru.romzheln.listing.model.entity.common.CommonPhysicalDetails;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.model.enums.ConstructionStage;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "house")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class House {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "property_id")
    private Property property;

    @Embedded
    private CommonPhysicalDetails commonPhysicalDetails;

    @Embedded
    private CommonLandDetails commonLandDetails;

    @Enumerated(EnumType.STRING)
    @Column(name = "construction_stage")
    private ConstructionStage constructionStage;

    @ManyToMany(mappedBy = "houses")
    @Builder.Default
    private Set<Communication> communications = new HashSet<>();

    @OneToMany(mappedBy = "house")
    @Builder.Default
    private Set<AdditionalBuilding> additionalBuildings = new HashSet<>();

    @Column(name = "land_plot_square")
    private BigDecimal landPlotSquare;
}
