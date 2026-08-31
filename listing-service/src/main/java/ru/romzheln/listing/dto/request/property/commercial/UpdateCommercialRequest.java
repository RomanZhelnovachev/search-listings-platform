package ru.romzheln.listing.dto.request.property.commercial;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.romzheln.listing.dto.common.CommercialPhysicalDetailsDto;
import ru.romzheln.listing.dto.common.CommonPhysicalDetailsDto;
import ru.romzheln.listing.dto.request.property.common.UpdatePropertyRequest;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommercialRequest extends UpdatePropertyRequest {

  private CommonPhysicalDetailsDto commonPhysicalDetailsDto;

  private CommercialPhysicalDetailsDto commercialPhysicalDetailsDto;

  private Set<Long> purposesIds;
}
