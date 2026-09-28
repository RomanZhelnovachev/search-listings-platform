package ru.romzheln.search_service.model.projection;

import jakarta.persistence.*;
import lombok.*;
import ru.romzheln.search_service.model.enums.CommunicationType;

@Entity
@Table(name = "communication_projections")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommunicationProjection {

  @Id private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "communication_type")
  private CommunicationType communicationType;

}
