package ru.romzheln.search_service.model.embeded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import ru.romzheln.search_service.model.enums.Elevator;
import ru.romzheln.search_service.model.enums.Ramp;
import ru.romzheln.search_service.model.enums.Side;

import java.math.BigDecimal;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ApartmentPhysicalDetails {

  @Column(name = "kitchen_square")
  private BigDecimal kitchenSquare;

  @Column(name = "floor")
  private Integer floor;

  @Enumerated(EnumType.STRING)
  @Column(name = "elevator")
  private Elevator elevator;

  @Enumerated(EnumType.STRING)
  @Column(name = "ramp")
  private Ramp ramp;

  @Enumerated(EnumType.STRING)
  @Column(name = "side")
  private Side side;
}
