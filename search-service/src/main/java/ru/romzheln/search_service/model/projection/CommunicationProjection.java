package ru.romzheln.search_service.model.projection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "communication_projections")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommunicationProjection {

  @Id private Long id;

  @Column(name = "communication_type")
  private String communicationType;

}
