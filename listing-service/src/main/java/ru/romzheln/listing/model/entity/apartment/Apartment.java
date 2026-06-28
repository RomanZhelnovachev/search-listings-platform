package ru.romzheln.listing.model.entity.apartment;

import jakarta.persistence.*;
import lombok.*;
import ru.romzheln.listing.model.entity.common.CommonPhysicalDetails;
import ru.romzheln.listing.model.entity.common.Developer;
import ru.romzheln.listing.model.entity.common.ResidentialComplex;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.model.enums.ApartmentType;
import ru.romzheln.listing.model.enums.Renovation;

@Entity
@Table(name = "apartment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Apartment {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "property_id")
    private Property property;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ApartmentType type;

    @Embedded
    private CommonPhysicalDetails commonPhysicalDetails;

    @Embedded
    private ApartmentPhysicalDetails apartmentPhysicalDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id")
    private Developer developer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complex_id")
    private ResidentialComplex complex;
}
