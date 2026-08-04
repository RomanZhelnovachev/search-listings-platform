package ru.romzheln.listing.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.romzheln.listing.dto.common.ApartmentPhysicalDetailsDto;
import ru.romzheln.listing.dto.common.CommonPhysicalDetailsDto;
import ru.romzheln.listing.model.enums.ApartmentType;

@Getter
@Builder
@Setter
public class ApartmentResponse extends PropertyResponse{

    private ApartmentType apartmentType;

    private CommonPhysicalDetailsDto commonPhysicalDetailsDto;

    private ApartmentPhysicalDetailsDto apartmentPhysicalDetailsDto;

    private Long developerId;

    private Long complexId;
}
