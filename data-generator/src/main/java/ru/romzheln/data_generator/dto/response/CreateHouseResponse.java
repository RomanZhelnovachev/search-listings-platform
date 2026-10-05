package ru.romzheln.data_generator.dto.response;

import java.math.BigDecimal;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.romzheln.data_generator.dto.CommonLandDetailsDto;
import ru.romzheln.data_generator.dto.CommonPhysicalDetailsDto;
import ru.romzheln.data_generator.enums.ConstructionStage;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateHouseResponse extends CreatePropertyResponse {

  private CommonPhysicalDetailsDto commonPhysicalDetailsDto;

  private CommonLandDetailsDto commonLandDetailsDto;

  private Long developerId;

  private Long complexId;

  private ConstructionStage constructionStage;

  private Set<Long> additionalBuildings;

  private BigDecimal landPlotSquare;
}
