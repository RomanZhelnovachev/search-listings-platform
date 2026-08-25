package ru.romzheln.search_service.model.embeded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommercialPhysicalDetails {

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "line")
    private String line;

    @Column(name = "property_location_type")
    private String propertyLocationType;

    @Column(name = "territorial_zone")
    private String territorialZone;

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
