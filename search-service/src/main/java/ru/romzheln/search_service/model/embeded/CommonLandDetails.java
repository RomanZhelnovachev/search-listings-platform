package ru.romzheln.search_service.model.embeded;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommonLandDetails {

    @Column(name = "land_use_id")
    private Long landUseId;

    @Column(name = "land_use_name")
    private String landUseName;

    @Column(name = "land_use_description")
    private String landUseDescription;

    @Column(name = "road")
    private String road;

    @Column(name = "fencing")
    private String fencing;
}
