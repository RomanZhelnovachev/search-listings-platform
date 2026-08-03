package ru.romzheln.listing.dto.request.property.house;

import lombok.Getter;
import ru.romzheln.listing.dto.request.property.common.CommonLandDetailsRequest;
import ru.romzheln.listing.dto.request.property.common.CommonPhysicalDetailsRequest;
import ru.romzheln.listing.dto.request.property.common.CreatePropertyRequest;
import ru.romzheln.listing.model.enums.ConstructionStage;

import java.math.BigDecimal;
import java.util.Set;

@Getter
public class CreateHouseRequest extends CreatePropertyRequest {

    private CommonPhysicalDetailsRequest commonPhysicalDetailsRequest;

    private CommonLandDetailsRequest commonLandDetailsRequest;

    private Long developerId;

    private Long complexId;

    private ConstructionStage constructionStage;

    private Set<Long> additionalBuildings;

    private BigDecimal landPlotSquare;
}
