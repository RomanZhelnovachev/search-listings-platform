package ru.romzheln.search_service.model.read_model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.romzheln.search_service.model.embeded.Commercial;

@Entity
@Table(name = "listing_commercial_search")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ListingCommercialReadModel extends ReadModel{

  @Embedded private Commercial commercial;
}
