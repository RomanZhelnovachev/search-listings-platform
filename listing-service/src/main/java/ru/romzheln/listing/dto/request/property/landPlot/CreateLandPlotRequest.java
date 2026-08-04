package ru.romzheln.listing.dto.request.property.landPlot;

import lombok.Getter;
import ru.romzheln.listing.dto.common.CommonLandDetailsDto;
import ru.romzheln.listing.dto.request.property.common.CreatePropertyRequest;

import java.util.Set;

@Getter
public class CreateLandPlotRequest extends CreatePropertyRequest {

    private CommonLandDetailsDto commonLandDetailsDto;

    private Set<Long> additionalBuildings;
}
