package ru.romzheln.listing.model.entity.owner;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.romzheln.listing.model.entity.listing.Listing;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "owners")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Owner {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "owner_seq"
    )
    @SequenceGenerator(
            name = "owner_seq",
            sequenceName = "owner_seq",
            allocationSize = 1
    )
    private Long id;

    @OneToOne(mappedBy = "owner")
    private OwnerDetails ownerDetails;

    @OneToMany(mappedBy = "owner", orphanRemoval = true, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Listing> listings = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;
}
