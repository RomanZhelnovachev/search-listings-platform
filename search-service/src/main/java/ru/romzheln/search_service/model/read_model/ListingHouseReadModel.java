package ru.romzheln.search_service.model.read_model;

import jakarta.persistence.*;
import lombok.*;
import ru.romzheln.search_service.model.embeded.House;
import ru.romzheln.search_service.model.embeded.Listing;
import java.time.Instant;

@Entity
@Table(name = "listing_house_search")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ListingHouseReadModel {

    @Id
    private Long id;

    @Embedded
    private Listing listing;

    @Embedded
    private House house;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;
}
