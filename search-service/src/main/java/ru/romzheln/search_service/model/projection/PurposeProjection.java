package ru.romzheln.search_service.model.projection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "purpose_projections")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PurposeProjection {

    @Id
    private Long id;

    @Column(name = "purpose_name")
    private String purposeName;
}
