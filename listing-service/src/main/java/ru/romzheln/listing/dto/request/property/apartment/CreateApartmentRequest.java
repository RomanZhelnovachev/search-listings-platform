package ru.romzheln.listing.dto.request.property.apartment;

import lombok.Getter;
import ru.romzheln.listing.dto.request.property.common.CommonPhysicalDetailsRequest;
import ru.romzheln.listing.dto.request.property.common.CreatePropertyRequest;
import ru.romzheln.listing.model.enums.ApartmentType;

@Getter
public class CreateApartmentRequest extends CreatePropertyRequest {

    private ApartmentType apartmentType;

    private CommonPhysicalDetailsRequest commonPhysicalDetailsRequest;

    private ApartmentPhysicalDetailsRequest apartmentPhysicalDetailsRequest;

    private Long developerId;

    private Long complexId;
}
