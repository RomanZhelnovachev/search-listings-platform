package ru.romzheln.listing.dto.request.property.commercial;

import lombok.Getter;
import ru.romzheln.listing.dto.request.property.common.CommonPhysicalDetailsRequest;
import ru.romzheln.listing.dto.request.property.common.UpdatePropertyRequest;

import java.util.Set;

@Getter
public class UpdateCommercialRequest extends UpdatePropertyRequest {

    private CommonPhysicalDetailsRequest commonPhysicalDetailsRequest;

    private CommercialPhysicalDetailsRequest commercialPhysicalDetailsRequest;

    private Set<Long> purposesIds;

}
