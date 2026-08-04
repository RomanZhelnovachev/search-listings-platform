package ru.romzheln.listing.model.entity.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.romzheln.listing.model.entity.house.House;
import ru.romzheln.listing.model.entity.landPlot.LandPlot;

import java.time.Instant;

@Entity
@Table(name = "additional_buildings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdditionalBuilding {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "additional_building_seq"
    )
    @SequenceGenerator(
            name = "additional_building_seq",
            sequenceName = "additional_building_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "land_plot_id")
    private LandPlot landPlot;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;
}
