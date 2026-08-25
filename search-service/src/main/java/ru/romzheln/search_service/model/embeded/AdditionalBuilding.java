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
public class AdditionalBuilding {

    @Column(name = "additional_building_id")
    private Long additionalBuildingId;

    @Column(name = "additional_building_name")
    private String additionalBuildingName;

    @Column(name = "additional_building_description")
    private String additionalBuildingDescription;
}
