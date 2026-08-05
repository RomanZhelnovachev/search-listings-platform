package ru.romzheln.listing.model.entity.listing;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.romzheln.listing.exception.*;
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

    @ElementCollection
    @CollectionTable(
            name = "listing_images",
            joinColumns = @JoinColumn(name = "listing_id")
    )
    @Column(name = "image_id")
    @Builder.Default
    private Set<Long> imageIds = new HashSet<>();

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

    @Enumerated(EnumType.STRING)
    @Column(name = "deal_type", nullable = false)
    private DealType dealType;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ElementCollection
    @CollectionTable(
            name = "listing_mortgage_programs",
            joinColumns = @JoinColumn(name = "listing_id")
    )
    @Column(name = "mortgage_program_id")
    @Builder.Default
    private Set<Long> mortgageProgramIds = new HashSet<>();

    @Column(name = "promotion_id")
    private Long promotionId;

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

    public void assignPromotion(Long promotionId) {
        if(promotionId == null){
            throw new ChangeListingException(id);        }
        validateEditable();
        if (Objects.equals(this.promotionId,
                promotionId)) {
            throw new ChangeListingException(id);
        }
        this.promotionId = promotionId;
    }

    public void disablePromotion(){
        validateEditable();
        if(this.promotionId == null){
            throw new PromotionAlreadyDisabled(id);
        }
        this.promotionId = null;
    }

    public void addMortgagePrograms(Set<Long> mortgagePrograms) {
        validateEditable();
        this.mortgageProgramIds.addAll(mortgagePrograms);
    }

    public void removeMortgagePrograms(Set<Long> programs) {
        validateEditable();
        if (!mortgageProgramIds.containsAll(programs)) {
            throw new MortgageProgramsRemovedException("Невозможно удалить несуществующие ипотечные программы");
        }
        mortgageProgramIds.removeAll(programs);
    }

    public Set<Long> addImages(Set<Long> images) {
        Set<Long> newImages = images.stream()
                .filter(image -> !this.imageIds.contains(image))
                .collect(Collectors.toSet());
        switch (status) {
            case CREATED -> this.imageIds.addAll(newImages);
            case PUBLISHED, APPROVED -> {
                this.imageIds.addAll(newImages);
                this.status = ListingStatus.CREATED;
            }
            case ARCHIVED, REMOVED -> throw new ChangeListingException(id);
        }
        if (newImages.isEmpty()) {
            throw new ImagesAlreadyAddedException("Нет новых изображений для добавления");
        }
        return newImages;
    }

    public void removeImages(Set<Long> images) {
        validateEditable();
        Set<Long> missingImages = images.stream()
                .filter(e -> !this.imageIds.contains(e))
                .collect(Collectors.toSet());
        if(!missingImages.isEmpty()){
            throw new ImageRemoveException("Невозможно удалить несуществующие изображения");
        }
        this.imageIds.removeAll(images);
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
