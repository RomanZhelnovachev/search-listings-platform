package ru.romzheln.listing.model.entity.common;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommonLandDetails {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "land_use_id")
    private LandUse landUse;

    @Column(name = "road")
    private String road;

    @Column(name = "fencing")
    private String fencing;


}
