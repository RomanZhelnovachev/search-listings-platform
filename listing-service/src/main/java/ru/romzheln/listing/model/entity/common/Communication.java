package ru.romzheln.listing.model.entity.common;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.model.enums.CommunicationType;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "communications")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Communication {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "communication_seq"
    )
    @SequenceGenerator(
            name = "communication_seq",
            sequenceName = "communication_seq",
            allocationSize = 1
    )
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "communication_type", nullable = false)
    private CommunicationType communicationType;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "communications")
    @Builder.Default
    private Set<Property> properties = new HashSet<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

}
