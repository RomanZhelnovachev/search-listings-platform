package ru.romzheln.listing.model.entity.house;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.romzheln.listing.model.entity.landplot.LandPlot;
import ru.romzheln.listing.model.enums.CommunicationType;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "communication")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Communication {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "communication_type")
    private CommunicationType communicationType;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(name = "communications_houses",
            joinColumns = @JoinColumn(name = "communication_id"),
            inverseJoinColumns = @JoinColumn(name = "house_id")
    )
    @Builder.Default
    private Set<House> houses = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "communications_land_plots",
            joinColumns = @JoinColumn(name = "communication_id"),
            inverseJoinColumns = @JoinColumn(name = "land_plot_id")
    )
    @Builder.Default
    private Set<LandPlot> landPlots = new HashSet<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

}
