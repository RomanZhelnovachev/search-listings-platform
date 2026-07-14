package ru.romzheln.listing.model.entity.property;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.romzheln.listing.model.entity.listing.Listing;
import ru.romzheln.listing.model.entity.apartment.Apartment;
import ru.romzheln.listing.model.entity.commercial.Commercial;
import ru.romzheln.listing.model.entity.house.House;
import ru.romzheln.listing.model.entity.landplot.LandPlot;
import ru.romzheln.listing.model.enums.Own;
import ru.romzheln.listing.model.enums.PropertyType;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "property")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Property {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Embedded
    private Location location;

    @Column(name = "square", nullable = false)
    private Double square;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PropertyType type;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private Apartment apartment;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private House house;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private LandPlot landPlot;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private Commercial commercial;

    @Enumerated(EnumType.STRING)
    @Column(name = "own")
    private Own own;

    @Column(name = "first_owner")
    private Boolean isFirstOwner;

    @OneToMany(mappedBy = "property", orphanRemoval = true, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Listing> listings = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;
}
