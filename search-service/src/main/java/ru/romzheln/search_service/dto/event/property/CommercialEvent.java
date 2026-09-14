package ru.romzheln.search_service.dto.event.property;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import ru.romzheln.search_service.dto.event.common.CommercialPhysicalDetailsDto;
import ru.romzheln.search_service.dto.event.common.CommonPhysicalDetailsDto;

@Getter
@Builder
public class CommercialEvent extends PropertyEvent {

  private CommonPhysicalDetailsDto commonPhysicalDetailsDto;

  private CommercialPhysicalDetailsDto commercialPhysicalDetailsDto;

  private Set<Long> purposesIds;
}
