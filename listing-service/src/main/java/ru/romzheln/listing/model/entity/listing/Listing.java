package ru.romzheln.listing.model.entity.listing;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.romzheln.listing.dto.event.listing.ChangePriceEvent;
import ru.romzheln.listing.exception.ChangeListingException;
import ru.romzheln.listing.exception.UpdateListingException;
import ru.romzheln.listing.model.entity.owner.Owner;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.model.enums.DealType;
import ru.romzheln.listing.model.enums.ListingStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "listings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Listing {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "listing_seq"
    )
    @SequenceGenerator(
            name = "listing_seq",
            sequenceName = "listing_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ListingStatus status;

    @ManyToMany
    @JoinTable(
            name = "listing_images",
            joinColumns = @JoinColumn(name = "listing_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    @Builder.Default
    private Set<Image> images = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

    @Enumerated(EnumType.STRING)
    @Column(name = "deal_type", nullable = false)
    private DealType dealType;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToMany(mappedBy = "listings")
    @Builder.Default
    private Set<MortgageProgram> mortgagePrograms = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    public void changeTitle(String title){
        validateEditable();
        this.title = title;
    }

    public void changeDescription(String description){
        validateEditable();
        this.description = description;
    }

    public void changeDealType(DealType dealType){
        validateEditable();
        this.dealType = dealType;
    }

    public ChangePriceEvent changePrice(BigDecimal newPrice) {
        validateEditable();
        if (newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new UpdateListingException(id);
        }
        if (this.price.compareTo(newPrice) == 0) {
            throw new ChangeListingException(id);
        }
        BigDecimal oldPrice = this.price;
        this.price = newPrice;
        return new ChangePriceEvent(oldPrice, newPrice);
    }

    private void validateEditable(){
        if(status == ListingStatus.ARCHIVED || status == ListingStatus.REMOVED){
            throw new ChangeListingException(id);
        }
    }
}
