package ru.romzheln.listing.model.entity.commercial;

import jakarta.persistence.*;
import lombok.*;
import ru.romzheln.listing.model.entity.property.Property;

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
}
