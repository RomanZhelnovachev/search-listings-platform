package ru.romzheln.search_service.model.embeded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import ru.romzheln.search_service.model.enums.Region;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "region")
    private Region region;
}
