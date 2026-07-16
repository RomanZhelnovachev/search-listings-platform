package ru.romzheln.listing.model.entity.commercial;

import jakarta.persistence.*;
import lombok.*;
import ru.romzheln.listing.model.entity.common.CommonPhysicalDetails;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.model.enums.TerritorialZone;
import ru.romzheln.listing.model.enums.Line;
import ru.romzheln.listing.model.enums.PropertyLocationType;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "commercial")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Commercial {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(name = "floor")
    private Integer floor;

    @Enumerated(EnumType.STRING)
    @Column(name = "line")
    private Line line;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_location_type")
    private PropertyLocationType propertyLocationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "territorial_zone")
    private TerritorialZone territorialZone;

    @Column(name = "separate_entrance")
    private Boolean separateEntrance;

    @Column(name = "hood")
    private Boolean hood;

    @Column(name = "tenant_exists")
    private Boolean tenantExists;

    @Column(name = "inputs")
    private Integer inputs;

    @Column(name = "electrical_power")
    private Integer electricalPower;

    @Embedded
    private CommonPhysicalDetails commonPhysicalDetails;

    @ManyToMany(mappedBy = "objects")
    @Builder.Default
    private Set<Purpose> purposes = new HashSet<>();

    @Column(name = "railway_dead_end")
    private Boolean railwayDeadEnd;
}
