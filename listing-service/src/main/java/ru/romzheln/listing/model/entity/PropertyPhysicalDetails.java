package ru.romzheln.listing.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.romzheln.listing.model.enums.Elevator;
import ru.romzheln.listing.model.enums.Heating;
import ru.romzheln.listing.model.enums.Ramp;
import ru.romzheln.listing.model.enums.WallMaterial;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "property_physical_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PropertyPhysicalDetails {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "material", nullable = false)
    private WallMaterial material;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    @Column(name = "year_built", nullable = false)
    private Integer yearBuilt;

    @Column(name = "floors_number")
    private Integer floorsNumber;

    @Column(name = "floor")
    private Integer floor;

    @Enumerated(EnumType.STRING)
    @Column(name = "heating", nullable = false)
    private Heating heating;

    @Enumerated(EnumType.STRING)
    @Column(name = "elevator", nullable = false)
    private Elevator elevator;

    @OneToOne
    @JoinColumn(name = "property_id", nullable = false, unique = true)
    private Property property;

    @Enumerated(EnumType.STRING)
    @Column(name = "ramp", nullable = false)
    private Ramp ramp;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;
}
