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
public class LandUse {

    @Column(name = "land_use_name")
    private String landUseName;

    @Column(name = "land_use_description")
    private String landUseDescription;
}
