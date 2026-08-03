package ru.romzheln.listing.model.entity.commercial;

import jakarta.persistence.*;
import lombok.*;
import ru.romzheln.listing.model.entity.common.CommonPhysicalDetails;
import ru.romzheln.listing.model.entity.property.Property;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "commercials")
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
    @JoinColumn(name = "id")
    private Property property;

    @Embedded
    private CommonPhysicalDetails commonPhysicalDetails;

    @Embedded
    private CommercialPhysicalDetails commercialPhysicalDetails;

    @ManyToMany(mappedBy = "objects")
    @Builder.Default
    private Set<Purpose> purposes = new HashSet<>();

}
