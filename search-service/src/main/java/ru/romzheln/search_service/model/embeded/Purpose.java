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
public class Purpose {

    @Column(name = "purpose_id")
    private Long purposeId;

    @Column(name = "purpose_name")
    private String purposeName;

    @Column(name = "purpose_description")
    private String purposeDescription;
}
