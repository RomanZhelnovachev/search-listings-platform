package ru.romzheln.listing.model.entity.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.romzheln.listing.model.entity.apartment.Apartment;
import ru.romzheln.listing.model.entity.house.House;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "residential_complex")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResidentialComplex {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "complex", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Apartment> apartments = new ArrayList<>();

    @OneToMany(mappedBy = "complex", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<House> houses = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;
}
