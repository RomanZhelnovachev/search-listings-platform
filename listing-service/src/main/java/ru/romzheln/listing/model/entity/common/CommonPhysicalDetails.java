package ru.romzheln.listing.model.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class CommonPhysicalDetails {

    /**
     * Количество комнат, где 0 - это студия
     */
    @Column(name = "rooms_number")
    private Integer roomsNumber;

    @Column(name = "ceiling_height")
    private BigDecimal ceilingHeight;

    @Enumerated(EnumType.STRING)
    @Column(name = "renovation")
    private Renovation renovation;

    @Enumerated(EnumType.STRING)
    @Column(name = "bathroom")
    private Bathroom bathroom;

    @Enumerated(EnumType.STRING)
    @Column(name = "material")
    private WallMaterial material;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    @Column(name = "year_built")
    private Integer yearBuilt;

    @Column(name = "floors_number")
    private Integer floorsNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "view")
    private WindowView view;

    @Enumerated(EnumType.STRING)
    @Column(name = "balcony")
    private Balcony balcony;

    @Enumerated(EnumType.STRING)
    @Column(name = "window_type")
    private WindowType windowType;

    @Enumerated(EnumType.STRING)
    @Column(name = "window_material")
    private WindowMaterial windowMaterial;

    @Enumerated(EnumType.STRING)
    @Column(name = "layout_feature")
    private LayoutFeature layoutFeature;

    @Enumerated(EnumType.STRING)
    @Column(name = "layout_type")
    private LayoutType layoutType;
}
