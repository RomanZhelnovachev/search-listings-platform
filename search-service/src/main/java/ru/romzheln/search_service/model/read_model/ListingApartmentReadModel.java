package ru.romzheln.search_service.model.read_model;

import jakarta.persistence.*;
import lombok.*;
import ru.romzheln.search_service.model.embeded.Apartment;
import ru.romzheln.search_service.model.embeded.Property;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "listing_apartment_search")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ListingApartmentReadModel {

    @Id
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "listing_status")
    private String listingStatus;

    @ElementCollection
    @CollectionTable(
            name = "listing_images",
            joinColumns = @JoinColumn(name = "listing_id")
    )
    @Column(name = "image_id")
    @Builder.Default
    private Set<Long> imageIds = new HashSet<>();

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "property_id")
    private Long propertyId;

    @Embedded
    private Property property;

    @Embedded
    private Apartment apartment;

    @Column(name = "deal_type")
    private String dealType;

    @Column(name = "price")
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

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;
}
