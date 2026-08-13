package ru.romzheln.search_service.model.embeded;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Apartment {

    @Column(name = "apartment_type")
    private String apartmentType;

    @Embedded
    private CommonPhysicalDetails commonPhysicalDetails;

    @Embedded
    private ApartmentPhysicalDetails apartmentPhysicalDetails;

    @Column(name = "developer_id")
    private Long developerId;

    @Column(name = "complex_id")
    private Long complexId;
}
