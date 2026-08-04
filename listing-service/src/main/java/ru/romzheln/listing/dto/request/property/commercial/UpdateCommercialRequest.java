package ru.romzheln.listing.dto.request.property.commercial;

import lombok.Getter;
import ru.romzheln.listing.dto.common.CommercialPhysicalDetailsDto;
import ru.romzheln.listing.dto.common.CommonPhysicalDetailsDto;
import ru.romzheln.listing.dto.request.property.common.UpdatePropertyRequest;

import java.util.Set;

@Getter
public class UpdateCommercialRequest extends UpdatePropertyRequest {

    private CommonPhysicalDetailsDto commonPhysicalDetailsDto;

    private CommercialPhysicalDetailsDto commercialPhysicalDetailsDto;

    private Set<Long> purposesIds;

}
