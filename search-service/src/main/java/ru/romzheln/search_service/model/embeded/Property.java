package ru.romzheln.search_service.model.embeded;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Property {

    @Embedded
    private Location location;

    @Column(name = "square")
    private BigDecimal square;

    @Column(name = "own")
    private String own;

    @Column(name = "first_owner")
    private Boolean firstOwner;

    @Builder.Default
    @ElementCollection
    @CollectionTable(
            name = "listing_communications",
            joinColumns = {
                    @JoinColumn(name = "listing_id", referencedColumnName = "id"),
                    @JoinColumn(name = "region", referencedColumnName = "region")
            }
    )
    @Column(name = "communication_id")
    Set<Long> communicationIds = new HashSet<>();
}
