package ru.romzheln.listing.model.entity.property;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import ru.romzheln.listing.model.enums.Region;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Location {

    @Enumerated(EnumType.STRING)
    @Column(name = "region", nullable = false)
    private Region region;

    @Column(name = "populated_area", nullable = false)
    private String populatedArea;

    @Column(name = "street")
    private String street;

    @Column(name = "house_number")
    private String house;

    @Column(name = "building_number")
    private String building;

    @Column(name = "apartment_number")
    private String apartment;
}
