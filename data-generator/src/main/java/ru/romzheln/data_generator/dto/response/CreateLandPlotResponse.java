package ru.romzheln.data_generator.dto.response;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.romzheln.data_generator.dto.CommonLandDetailsDto;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLandPlotResponse extends CreatePropertyResponse {

  private CommonLandDetailsDto commonLandDetailsDto;

  private Set<Long> additionalBuildings;
}
