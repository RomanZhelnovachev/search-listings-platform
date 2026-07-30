package ru.romzheln.listing.model.entity.listing;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.romzheln.listing.exception.*;
import ru.romzheln.listing.model.entity.owner.Owner;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.model.enums.DealType;
import ru.romzheln.listing.model.enums.ListingStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    public void changeTitle(String title) {
        validateEditable();
        this.title = title;
    }

    public void changeDescription(String description) {
        validateEditable();
        this.description = description;
    }

    public void changeDealType(DealType dealType) {
        validateEditable();
        this.dealType = dealType;
    }

    public BigDecimal changePrice(BigDecimal newPrice) {
        validateEditable();
        if (newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new UpdateListingException(id);
        }
        if (this.price.compareTo(newPrice) == 0) {
            throw new ChangeListingException(id);
        }
        BigDecimal oldPrice = this.price;
        this.price = newPrice;
        return oldPrice;
    }

    public void assignPromotion(Promotion promotion) {
        validateEditable();
        if (Objects.equals(this.promotion,
                promotion) || !promotion.getActive()) {
            throw new ChangeListingException(id);
        }
        this.promotion = promotion;
    }

    public void disablePromotion(){
        validateEditable();
        if(this.promotion == null){
            throw new PromotionAlreadyDisabled(id);
        }
        this.promotion = null;
    }

    public void addMortgagePrograms(Set<MortgageProgram> mortgagePrograms) {
        validateEditable();
        this.mortgagePrograms.addAll(mortgagePrograms.stream()
                .filter(MortgageProgram::getActive)
                .collect(Collectors.toSet())
        );
    }

    public void removeMortgagePrograms(Set<MortgageProgram> programs) {
        validateEditable();
        if (!mortgagePrograms.containsAll(programs)) {
            throw new MortgageProgramsRemovedException("Невозможно удалить несуществующие ипотечные программы");
        }
        mortgagePrograms.removeAll(programs);
    }

    public Set<Image> addImages(Set<Image> images) {
        Set<Image> newImages = images.stream()
                .filter(image -> !this.images.contains(image))
                .collect(Collectors.toSet());
        switch (status) {
            case CREATED -> this.images.addAll(newImages);
            case PUBLISHED, APPROVED -> {
                this.images.addAll(newImages);
                this.status = ListingStatus.CREATED;
            }
            case ARCHIVED, REMOVED -> throw new ChangeListingException(id);
        }
        if (newImages.isEmpty()) {
            throw new ImagesAlreadyAddedException("Нет новых изображений для добавления");
        }
        return newImages;
    }

    public void removeImages(Set<Image> images) {
        validateEditable();
        Set<Image> missingImages = images.stream()
                .filter(e -> !this.images.contains(e))
                .collect(Collectors.toSet());
        if(!missingImages.isEmpty()){
            throw new ImageRemoveException("Невозможно удалить несуществующие изображения");
        }
        this.images.removeAll(images);
    }

    public void publish() {
        switch (status) {
            case CREATED -> throw new PublishingNotModeratedException(id);
            case APPROVED, ARCHIVED -> status = ListingStatus.PUBLISHED;
            case PUBLISHED -> throw new ListingAlreadyPublished(id);
            case REMOVED -> throw new PublishingRemovedListingException(id);
        }
    }

    public void archive() {
        if (this.status != ListingStatus.PUBLISHED) {
            throw new ListingArchiveException(id,
                    this.status);
        }
        this.status = ListingStatus.ARCHIVED;
    }

    public void approve() {
        if (status != ListingStatus.CREATED) {
            throw new ListingAlreadyApproved(id,
                    this.status);
        }
        this.status = ListingStatus.APPROVED;
    }

    public void remove() {
        if (this.status == ListingStatus.REMOVED) {
            throw new ListingAlreadyRemoved(id);
        }
        this.status = ListingStatus.REMOVED;
    }

    private void validateEditable() {
        if (status == ListingStatus.ARCHIVED || status == ListingStatus.REMOVED) {
            throw new ChangeListingException(id);
        }
    }
}
