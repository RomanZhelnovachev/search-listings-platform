package ru.romzheln.search_service.model.embeded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

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

    @Column(name = "elevator")
    private String elevator;

    @Column(name = "ramp")
    private String ramp;

    @Column(name = "side")
    private String side;
}
