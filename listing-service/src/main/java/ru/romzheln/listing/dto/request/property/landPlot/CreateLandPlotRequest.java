package ru.romzheln.listing.dto.request.property.landPlot;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.romzheln.listing.dto.common.CommonLandDetailsDto;
import ru.romzheln.listing.dto.request.property.common.CreatePropertyRequest;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLandPlotRequest extends CreatePropertyRequest {

  private CommonLandDetailsDto commonLandDetailsDto;

  private Set<Long> additionalBuildings;
}
