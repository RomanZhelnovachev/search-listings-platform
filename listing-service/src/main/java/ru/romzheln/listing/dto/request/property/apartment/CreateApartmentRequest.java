package ru.romzheln.listing.dto.request.property.apartment;

import lombok.Getter;
import ru.romzheln.listing.dto.common.ApartmentPhysicalDetailsDto;
import ru.romzheln.listing.dto.common.CommonPhysicalDetailsDto;
import ru.romzheln.listing.dto.request.property.common.CreatePropertyRequest;
import ru.romzheln.listing.model.enums.ApartmentType;

@Getter
public class CreateApartmentRequest extends CreatePropertyRequest {

    private ApartmentType apartmentType;

    private CommonPhysicalDetailsDto commonPhysicalDetailsDto;

    private ApartmentPhysicalDetailsDto apartmentPhysicalDetailsDto;

    private Long developerId;

    private Long complexId;
}
