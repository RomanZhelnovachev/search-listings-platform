package ru.romzheln.search_service.model.read_model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.romzheln.search_service.model.embeded.Apartment;

@Entity
@Table(name = "listing_apartment_search")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ListingApartmentReadModel extends ReadModel{    

    @Embedded
    private Apartment apartment;    
}
