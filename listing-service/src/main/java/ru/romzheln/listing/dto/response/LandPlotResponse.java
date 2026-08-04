package ru.romzheln.listing.dto.response;

import lombok.Builder;
import lombok.Getter;
import ru.romzheln.listing.dto.common.CommonLandDetailsDto;

import java.util.Set;

@Getter
@Builder
public class LandPlotResponse extends PropertyResponse{

    private CommonLandDetailsDto commonLandDetailsDto;

    private Set<Long> additionalBuildings;
}
