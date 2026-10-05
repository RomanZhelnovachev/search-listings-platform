package ru.romzheln.data_generator.dto.response;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.romzheln.data_generator.dto.CommercialPhysicalDetailsDto;
import ru.romzheln.data_generator.dto.CommonPhysicalDetailsDto;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommercialResponse extends CreatePropertyResponse {

    private CommonPhysicalDetailsDto commonPhysicalDetailsDto;

    private CommercialPhysicalDetailsDto commercialPhysicalDetailsDto;

    private Set<Long> purposesIds;
}
