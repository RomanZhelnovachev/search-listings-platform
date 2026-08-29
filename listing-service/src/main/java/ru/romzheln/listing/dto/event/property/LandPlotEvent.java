package ru.romzheln.listing.dto.event.property;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import ru.romzheln.listing.dto.common.CommonLandDetailsDto;

@Getter
@Builder
public class LandPlotEvent extends PropertyEvent {

  private CommonLandDetailsDto commonLandDetailsDto;

  private Set<Long> additionalBuildings;
}
