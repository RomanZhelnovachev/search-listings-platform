package ru.romzheln.search_service.model.embeded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResidentialComplex {

  @Column(name = "complex_id")
  private Long complexId;

  @Column(name = "complex_name")
  private String complexName;
}
