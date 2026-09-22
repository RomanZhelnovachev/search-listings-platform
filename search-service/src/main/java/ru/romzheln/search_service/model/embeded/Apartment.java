package ru.romzheln.search_service.model.embeded;

import jakarta.persistence.*;
import lombok.*;
import ru.romzheln.search_service.model.enums.ApartmentType;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Apartment {

  @Enumerated(EnumType.STRING)
  @Column(name = "apartment_type")
  private ApartmentType apartmentType;

  @Embedded private CommonPhysicalDetails commonPhysicalDetails;

  @Embedded private ApartmentPhysicalDetails apartmentPhysicalDetails;

  @Embedded private Developer developer;

  @Embedded private ResidentialComplex complex;
}
