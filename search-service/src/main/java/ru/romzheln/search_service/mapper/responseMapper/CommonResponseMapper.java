package ru.romzheln.search_service.mapper.responseMapper;

import org.mapstruct.Mapper;
import ru.romzheln.search_service.dto.response.commonDto.*;
import ru.romzheln.search_service.model.embeded.*;

@Mapper(componentModel = "spring")
public interface CommonResponseMapper {

    CommonPhysicalDetailsDto toCommonPhysicalDetailsDto(CommonPhysicalDetails details);

    ListingDto toListingDto(Listing listing);

    PropertyDto toPropertyDto(Property property);

    LocationDto toLocationDto(Location location);
    
    CommonLandDetailsDto toCommonLandDetailsDto(CommonLandDetails details);
}
