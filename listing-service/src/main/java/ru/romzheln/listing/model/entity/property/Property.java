package ru.romzheln.listing.model.entity.property;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.romzheln.listing.exception.ChangePropertyException;
import ru.romzheln.listing.exception.ChangefirstOwnerException;
import ru.romzheln.listing.model.entity.common.Communication;
import ru.romzheln.listing.model.entity.listing.Listing;
import ru.romzheln.listing.model.entity.apartment.Apartment;
import ru.romzheln.listing.model.entity.commercial.Commercial;
import ru.romzheln.listing.model.entity.house.House;
import ru.romzheln.listing.model.entity.landPlot.LandPlot;
import ru.romzheln.listing.model.enums.Own;
import ru.romzheln.listing.model.enums.PropertyType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "properties")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Property {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "property_seq"
    )
    @SequenceGenerator(
            name = "property_seq",
            sequenceName = "property_seq",
            allocationSize = 1
    )
    private Long id;

    @Embedded
    private Location location;

    @Column(name = "square", nullable = false)
    private BigDecimal square;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_type", nullable = false)
    private PropertyType propertyType;

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
    @Setter(AccessLevel.NONE)
    private Boolean firstOwner;

    @ManyToMany
    @JoinTable(
            name = "property_communications",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "communication_id")
    )
    @Builder.Default
    private Set<Communication> communications = new HashSet<>();

    @OneToMany(mappedBy = "property", orphanRemoval = true, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Listing> listings = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    public void changeFirstOwner(Boolean newData){
        if (Boolean.FALSE.equals(this.firstOwner)){
            throw new ChangefirstOwnerException("Невозможно изменить");
        }
        if(this.firstOwner.equals(newData)){
            throw new ChangePropertyException(id);
        }
       this.firstOwner = newData;
    }
}
