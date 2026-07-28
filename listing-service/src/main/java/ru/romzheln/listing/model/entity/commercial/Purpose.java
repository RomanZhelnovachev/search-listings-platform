package ru.romzheln.listing.model.entity.commercial;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "purposes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Purpose {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "purpose_seq"
    )
    @SequenceGenerator(
            name = "purpose_seq",
            sequenceName = "purpose_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "purpose_commercials",
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
