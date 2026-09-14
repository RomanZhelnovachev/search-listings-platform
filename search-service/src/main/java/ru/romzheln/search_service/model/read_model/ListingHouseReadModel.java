package ru.romzheln.search_service.model.read_model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.romzheln.search_service.model.embeded.House;

@Entity
@Table(name = "listing_house_search")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ListingHouseReadModel extends ReadModel{

  @Embedded private House house;
}
