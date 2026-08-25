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
public class Location {

    @Column(name = "region")
    private String region;

    @Column(name = "populated_area")
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
