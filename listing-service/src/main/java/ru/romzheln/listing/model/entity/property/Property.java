package ru.romzheln.listing.model.entity.property;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.romzheln.listing.model.entity.common.Mortgage;
import ru.romzheln.listing.model.entity.common.Promotion;
import ru.romzheln.listing.model.entity.apartment.Apartment;
import ru.romzheln.listing.model.entity.commercial.Commercial;
import ru.romzheln.listing.model.entity.house.House;
import ru.romzheln.listing.model.entity.landplot.LandPlot;
import ru.romzheln.listing.model.enums.DealType;
import ru.romzheln.listing.model.enums.Own;
import ru.romzheln.listing.model.enums.PropertyType;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "property")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Property {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Embedded
    private Location location;

    @Column(name = "square", nullable = false)
    private Double square;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PropertyType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "deal_type", nullable = false)
    private DealType dealType;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mortgage_id")
    private Mortgage mortgage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private Apartment apartment;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private House house;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private LandPlot landPlot;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private Commercial commercial;

    @Enumerated(EnumType.STRING)
    @Column(name = "own")
    private Own own;

    @Column(name = "first_owner")
    private Boolean isFirstOwner;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;
}
