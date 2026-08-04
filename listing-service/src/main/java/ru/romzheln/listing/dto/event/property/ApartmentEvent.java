package ru.romzheln.listing.dto.event.property;

import lombok.Builder;
import lombok.Getter;
import ru.romzheln.listing.dto.common.ApartmentPhysicalDetailsDto;
import ru.romzheln.listing.dto.common.CommonPhysicalDetailsDto;
import ru.romzheln.listing.model.enums.ApartmentType;

@Getter
@Builder
public class ApartmentEvent extends PropertyEvent {

    private ApartmentType apartmentType;

    private CommonPhysicalDetailsDto commonPhysicalDetailsDto;

    private ApartmentPhysicalDetailsDto apartmentPhysicalDetailsDto;

    private Long developerId;

    private Long complexId;
}
