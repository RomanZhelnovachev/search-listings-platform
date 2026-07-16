package ru.romzheln.listing.model.entity.commercial;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "purpose")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Purpose {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "purpose_commercial",
            joinColumns = @JoinColumn(name = "purpose_id"),
            inverseJoinColumns = @JoinColumn(name = "commercial_id")
    )
    @Builder.Default
    private Set<Commercial> objects = new HashSet<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;
}
