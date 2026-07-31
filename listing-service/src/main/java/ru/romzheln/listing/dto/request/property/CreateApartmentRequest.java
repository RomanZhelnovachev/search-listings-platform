package ru.romzheln.listing.dto.request.property;

import lombok.Getter;
import ru.romzheln.listing.model.enums.ApartmentType;

@Getter
public class CreateApartmentRequest extends CreatePropertyRequest {

    private ApartmentType apartmentType;

    private CommonPhysicalDetailsRequest commonPhysicalDetailsRequest;

    private ApartmentPhysicalDetailsRequest apartmentPhysicalDetailsRequest;

    private Long developerId;

    private Long complexId;
}
