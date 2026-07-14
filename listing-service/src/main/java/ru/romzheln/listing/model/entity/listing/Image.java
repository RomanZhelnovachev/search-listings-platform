package ru.romzheln.listing.model.entity.listing;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "images",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"name", "path"})
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Image {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "path", nullable = false)
    private String path;

    @ManyToMany(mappedBy = "images")
    @Builder.Default
    private Set<Listing> listings = new HashSet<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;
}
