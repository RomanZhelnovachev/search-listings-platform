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

    @ElementCollection
    @CollectionTable(
            name = "property_communication",
            joinColumns = @JoinColumn(
                    name = "property_id",
                    referencedColumnName = "property_id")
    )
    @Builder.Default
    Set<Communication> communicationIds = new HashSet<>();
}
