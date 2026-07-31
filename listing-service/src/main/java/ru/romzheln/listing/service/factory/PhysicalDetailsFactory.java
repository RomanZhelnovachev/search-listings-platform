package ru.romzheln.listing.service.factory;

import org.springframework.stereotype.Component;
import ru.romzheln.listing.dto.request.property.ApartmentPhysicalDetailsRequest;
import ru.romzheln.listing.dto.request.property.CommonPhysicalDetailsRequest;
import ru.romzheln.listing.model.entity.apartment.ApartmentPhysicalDetails;
import ru.romzheln.listing.model.entity.common.CommonPhysicalDetails;

@Component
public class PhysicalDetailsFactory {

    public CommonPhysicalDetails buildCommonPhysicalDetails(CommonPhysicalDetailsRequest request){
        return CommonPhysicalDetails.builder()
                .roomsNumber(request.roomsNumber())
                .ceilingHeight(request.ceilingHeight())
                .renovation(request.renovation())
                .bathroom(request.bathroom())
                .material(request.material())
                .completionDate(request.completionDate())
                .yearBuilt(request.yearBuilt())
                .floorsNumber(request.floorsNumber())
                .view(request.view())
                .balcony(request.balcony())
                .windowType(request.windowType())
                .windowMaterial(request.windowMaterial())
                .layoutFeature(request.layoutFeature())
                .layoutType(request.layoutType())
                .build();
    }

    public ApartmentPhysicalDetails buildApartmentPhysicalDetails(ApartmentPhysicalDetailsRequest request){
        return ApartmentPhysicalDetails.builder()
                .kitchenSquare(request.kitchenSquare())
                .floor(request.floor())
                .elevator(request.elevator())
                .ramp(request.ramp())
                .side(request.side())
                .build();
    }
}
