package ru.romzheln.listing.dto.request.property.house;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.romzheln.listing.dto.common.CommonLandDetailsDto;
import ru.romzheln.listing.dto.common.CommonPhysicalDetailsDto;
import ru.romzheln.listing.dto.request.property.common.UpdatePropertyRequest;
import ru.romzheln.listing.model.enums.ConstructionStage;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateHouseRequest extends UpdatePropertyRequest {

    private CommonPhysicalDetailsDto commonPhysicalDetailsDto;

    private CommonLandDetailsDto commonLandDetailsDto;

    private Long developerId;

    private Long complexId;

    private ConstructionStage constructionStage;

    private Set<Long> additionalBuildings;

    private BigDecimal landPlotSquare;

}
