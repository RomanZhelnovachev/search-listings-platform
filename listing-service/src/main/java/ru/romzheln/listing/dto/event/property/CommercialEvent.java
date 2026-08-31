package ru.romzheln.listing.dto.event.property;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import ru.romzheln.listing.dto.common.CommercialPhysicalDetailsDto;
import ru.romzheln.listing.dto.common.CommonPhysicalDetailsDto;

@Getter
@Builder
public class CommercialEvent extends PropertyEvent {

  private CommonPhysicalDetailsDto commonPhysicalDetailsDto;

  private CommercialPhysicalDetailsDto commercialPhysicalDetailsDto;

  private Set<Long> purposesIds;
}
