package ru.romzheln.search_service.model.embeded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommonPhysicalDetails {

    @Column(name = "rooms_number")
    private Integer roomsNumber;

    @Column(name = "ceiling_height")
    private BigDecimal ceilingHeight;

    @Enumerated(EnumType.STRING)
    @Column(name = "renovation")
    private String renovation;

    @Column(name = "bathroom")
    private String bathroom;

    @Column(name = "material")
    private String material;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    @Column(name = "year_built")
    private Integer yearBuilt;

    @Column(name = "floors_number")
    private Integer floorsNumber;

    @Column(name = "view")
    private String view;

    @Column(name = "balcony")
    private String balcony;

    @Column(name = "window_type")
    private String windowType;

    @Column(name = "window_material")
    private String windowMaterial;

    @Column(name = "layout_feature")
    private String layoutFeature;

    @Column(name = "layout_type")
    private String layoutType;
}
