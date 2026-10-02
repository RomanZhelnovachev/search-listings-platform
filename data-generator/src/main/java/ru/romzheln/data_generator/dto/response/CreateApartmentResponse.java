package ru.romzheln.data_generator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.romzheln.data_generator.dto.ApartmentPhysicalDetailsDto;
import ru.romzheln.data_generator.dto.CommonPhysicalDetailsDto;
import ru.romzheln.data_generator.enums.ApartmentType;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateApartmentResponse extends CreatePropertyResponse {

  private ApartmentType apartmentType;

  private CommonPhysicalDetailsDto commonPhysicalDetailsDto;

  private ApartmentPhysicalDetailsDto apartmentPhysicalDetailsDto;

  private Long developerId;

  private Long complexId;
}
