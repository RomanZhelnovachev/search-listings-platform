package ru.romzheln.search_service.model.embeded;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Commercial {

    @Embedded
    private CommonPhysicalDetails commonPhysicalDetails;

    @Embedded
    private CommercialPhysicalDetails commercialPhysicalDetails;

    @ElementCollection
    @CollectionTable(
            name = "property_purposes",
            joinColumns = @JoinColumn(
                    name = "property_id",
                    referencedColumnName = "property_id")
    )
    @Builder.Default
    private Set<Purpose> purposes = new HashSet<>();
}
