package ru.romzheln.listing.model.entity.listing;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "mortgage_programs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MortgageProgram {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "mortgage_program_seq"
    )
    @SequenceGenerator(
            name = "mortgage_program_seq",
            sequenceName = "mortgage_program_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "active", nullable = false)
    @Builder.Default
    private Boolean active = true;

    @ManyToMany
    @JoinTable(
            name = "mortgage_program_listings",
            joinColumns = @JoinColumn(name = "mortgage_program_id"),
            inverseJoinColumns = @JoinColumn(name = "listing_id")
    )
   @Builder.Default
    private Set<Listing> listings = new HashSet<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;
}
