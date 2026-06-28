package ru.romzheln.listing.model.entity.landplot;

import jakarta.persistence.*;
import lombok.*;
import ru.romzheln.listing.model.entity.property.Property;

@Entity
@Table(name = "land_plot")
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
    @JoinColumn(name = "property_id")
    private Property property;
}
