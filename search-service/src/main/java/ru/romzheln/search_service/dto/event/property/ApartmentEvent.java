package ru.romzheln.search_service.dto.event.property;

import lombok.Builder;
import lombok.Getter;
import ru.romzheln.search_service.dto.event.common.ApartmentPhysicalDetailsDto;
import ru.romzheln.search_service.dto.event.common.CommonPhysicalDetailsDto;

@Getter
@Builder
public class ApartmentEvent extends PropertyEvent {

  private String apartmentType;

  private CommonPhysicalDetailsDto commonPhysicalDetailsDto;

  private ApartmentPhysicalDetailsDto apartmentPhysicalDetailsDto;

  private Long developerId;

  private String developerName;

  private Long complexId;

  private String complexName;
}
