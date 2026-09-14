package ru.romzheln.listing.dto.event.property;

import java.math.BigDecimal;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import ru.romzheln.listing.dto.common.CommonLandDetailsDto;
import ru.romzheln.listing.dto.common.CommonPhysicalDetailsDto;
import ru.romzheln.listing.model.enums.ConstructionStage;

@Getter
@Builder
public class HouseEvent extends PropertyEvent {

  private CommonPhysicalDetailsDto commonPhysicalDetailsDto;

  private CommonLandDetailsDto commonLandDetailsDto;

  private Long developerId;

  private String developerName;

  private Long complexId;

  private String complexName;

  private ConstructionStage constructionStage;

  private Set<Long> additionalBuildings;

  private BigDecimal landPlotSquare;
}
