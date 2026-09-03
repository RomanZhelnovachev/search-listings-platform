package ru.romzheln.search_service.model.read_model;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;
import ru.romzheln.search_service.model.embeded.House;
import ru.romzheln.search_service.model.embeded.Listing;
import ru.romzheln.search_service.model.embeded.ListingKey;

@Entity
@Table(name = "listing_house_search")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ListingHouseReadModel implements ReadModel{

  @EmbeddedId private ListingKey key;

  @Embedded private Listing listing;

  @Embedded private House house;

  @Column(name = "created_at")
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;
}
