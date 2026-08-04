package ru.romzheln.listing.dto.request.property.apartment;

import lombok.Getter;
import ru.romzheln.listing.dto.common.ApartmentPhysicalDetailsDto;
import ru.romzheln.listing.dto.common.CommonPhysicalDetailsDto;
import ru.romzheln.listing.dto.request.property.common.UpdatePropertyRequest;

@Getter
public class UpdateApartmentRequest extends UpdatePropertyRequest {

    private CommonPhysicalDetailsDto commonPhysicalDetailsDto;

    private ApartmentPhysicalDetailsDto apartmentPhysicalDetailsDto;

    private Long developerId;

    private Long complexId;
}
