package ru.romzheln.search_service.dto.event.property;

import java.math.BigDecimal;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import ru.romzheln.search_service.dto.event.common.CommonLandDetailsDto;
import ru.romzheln.search_service.dto.event.common.CommonPhysicalDetailsDto;

@Getter
@Builder
public class HouseEvent extends PropertyEvent {

  private CommonPhysicalDetailsDto commonPhysicalDetailsDto;

  private CommonLandDetailsDto commonLandDetailsDto;

  private Long developerId;

  private String developerName;

  private Long complexId;

  private String complexName;

  private String constructionStage;

  private Set<Long> additionalBuildings;

  private BigDecimal landPlotSquare;
}
