package ru.romzheln.listing.dto.request.property.landPlot;

import lombok.Getter;
import ru.romzheln.listing.dto.common.CommonLandDetailsDto;
import ru.romzheln.listing.dto.request.property.common.UpdatePropertyRequest;

import java.util.Set;

@Getter
public class UpdateLandPlotRequest extends UpdatePropertyRequest {

    private CommonLandDetailsDto commonLandDetailsDto;

    private Set<Long> additionalBuildings;
}
