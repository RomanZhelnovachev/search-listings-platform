package ru.romzheln.listing.model.entity.common;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "land_use_types")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LandUse {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "land_use_seq"
    )
    @SequenceGenerator(
            name = "land_use_seq",
            sequenceName = "land_use_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;
}
