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
@EqualsAndHashCode
public class ListingKey {

    @Column(name = "id")
    private Long id;

    @Column(name = "region")
    private String region;
}
