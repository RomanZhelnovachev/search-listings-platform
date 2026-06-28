package ru.romzheln.listing.model.entity.apartment;

import jakarta.persistence.*;
import lombok.*;
import ru.romzheln.listing.model.enums.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ApartmentPhysicalDetails {

    @Column(name = "kitchen_square")
    private Double kitchenSquare;

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
