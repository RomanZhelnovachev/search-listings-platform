package ru.romzheln.listing.model.entity.commercial;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import ru.romzheln.listing.model.enums.Line;
import ru.romzheln.listing.model.enums.PropertyLocationType;
import ru.romzheln.listing.model.enums.TerritorialZone;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommercialPhysicalDetails {

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

    @Column(name = "ventilation_exists")
    private Boolean ventilation;

    @Column(name = "tenant_exists")
    private Boolean tenantExists;

    @Column(name = "entrances_number")
    private Integer entrancesNumber;

    @Column(name = "electrical_power_kw")
    private Integer electricalPowerKw;

    @Column(name = "railway_dead_end")
    private Boolean railwayDeadEnd;
}
