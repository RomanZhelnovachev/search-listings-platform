package ru.romzheln.listing.dto.request.property.apartment;

import lombok.Getter;
import ru.romzheln.listing.dto.request.property.common.CommonPhysicalDetailsRequest;
import ru.romzheln.listing.dto.request.property.common.UpdatePropertyRequest;

@Getter
public class UpdateApartmentRequest extends UpdatePropertyRequest {

    private CommonPhysicalDetailsRequest commonPhysicalDetailsRequest;

    private ApartmentPhysicalDetailsRequest apartmentPhysicalDetailsRequest;

    private Long developerId;

    private Long complexId;
}
