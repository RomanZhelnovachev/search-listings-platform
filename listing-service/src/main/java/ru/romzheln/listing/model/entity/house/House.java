package ru.romzheln.listing.model.entity.house;

import jakarta.persistence.*;
import lombok.*;
import ru.romzheln.listing.model.entity.property.Property;

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
}
