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
public class Communication {

    @Column(name = "communication_id")
    private Long communicationId;

    @Column(name = "communication_type")
    private String communicationType;

    @Column(name = "communication_description")
    private String communicationDescription;
}
