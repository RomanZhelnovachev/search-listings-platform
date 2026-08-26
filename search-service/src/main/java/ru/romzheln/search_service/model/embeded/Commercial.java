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
            name = "listing_purposes",
            joinColumns = {
                    @JoinColumn(name = "listing_id", referencedColumnName = "id"),
                    @JoinColumn(name = "region", referencedColumnName = "region")
            }
    )
    @Column(name = "purpose_id")
    @Builder.Default
    private Set<Long> purposeIds = new HashSet<>();
}
