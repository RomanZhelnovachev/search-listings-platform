package ru.romzheln.listing.model.entity.common;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.romzheln.listing.model.entity.apartment.Apartment;
import ru.romzheln.listing.model.entity.house.House;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "residential_complexes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResidentialComplex {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "residential_complex_seq"
    )
    @SequenceGenerator(
            name = "residential_complex_seq",
            sequenceName = "residential_complex_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "complex")
    @Builder.Default
    private List<Apartment> apartments = new ArrayList<>();

    @OneToMany(mappedBy = "complex")
    @Builder.Default
    private List<House> houses = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;
}
