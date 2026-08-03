package ru.romzheln.listing.mapper;

import org.springframework.stereotype.Component;
import ru.romzheln.listing.dto.request.property.apartment.ApartmentPhysicalDetailsRequest;
import ru.romzheln.listing.dto.request.property.commercial.CommercialPhysicalDetailsRequest;
import ru.romzheln.listing.dto.request.property.common.CommonLandDetailsRequest;
import ru.romzheln.listing.dto.request.property.common.CommonPhysicalDetailsRequest;
import ru.romzheln.listing.model.entity.apartment.ApartmentPhysicalDetails;
import ru.romzheln.listing.model.entity.commercial.CommercialPhysicalDetails;
import ru.romzheln.listing.model.entity.common.CommonLandDetails;
import ru.romzheln.listing.model.entity.common.CommonPhysicalDetails;
import ru.romzheln.listing.model.entity.common.LandUse;
import ru.romzheln.listing.util.UpdateUtil;

@Component
public class PhysicalDetailsMapper {

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

    public CommercialPhysicalDetails buildCommercialPhysicalDetails(CommercialPhysicalDetailsRequest request){
        return CommercialPhysicalDetails.builder()
                .floor(request.floor())
                .line(request.line())
                .propertyLocationType(request.propertyLocationType())
                .territorialZone(request.territorialZone())
                .separateEntrance(request.separateEntrance())
                .ventilation(request.ventilation())
                .tenantExists(request.tenantExists())
                .entrancesNumber(request.entrancesNumber())
                .electricalPowerKw(request.electricalPowerKw())
                .railwayDeadEnd(request.railwayDeadEnd())
                .build();
    }

    public CommonLandDetails buildCommonLandDetails(CommonLandDetailsRequest request, LandUse landUse){
        return CommonLandDetails.builder()
                .landUse(landUse)
                .road(request.road())
                .fencing(request.fencing())
                .build();
    }

    public void updateCommonPhysicalDetails(CommonPhysicalDetails details, CommonPhysicalDetailsRequest request){
        UpdateUtil.setIfNotNull(request.roomsNumber(), details::setRoomsNumber);
        UpdateUtil.setIfNotNull(request.ceilingHeight(), details::setCeilingHeight);
        UpdateUtil.setIfNotNull(request.renovation(), details::setRenovation);
        UpdateUtil.setIfNotNull(request.bathroom(), details::setBathroom);
        UpdateUtil.setIfNotNull(request.material(), details::setMaterial);
        UpdateUtil.setIfNotNull(request.completionDate(), details::setCompletionDate);
        UpdateUtil.setIfNotNull(request.yearBuilt(), details::setYearBuilt);
        UpdateUtil.setIfNotNull(request.floorsNumber(), details::setFloorsNumber);
        UpdateUtil.setIfNotNull(request.view(), details::setView);
        UpdateUtil.setIfNotNull(request.balcony(), details::setBalcony);
        UpdateUtil.setIfNotNull(request.windowType(), details::setWindowType);
        UpdateUtil.setIfNotNull(request.windowMaterial(), details::setWindowMaterial);
        UpdateUtil.setIfNotNull(request.layoutFeature(), details::setLayoutFeature);
        UpdateUtil.setIfNotNull(request.layoutType(), details::setLayoutType);
    }

    public void updateApartmentPhysicalDetails(ApartmentPhysicalDetails details, ApartmentPhysicalDetailsRequest request){
        UpdateUtil.setIfNotNull(request.kitchenSquare(), details::setKitchenSquare);
        UpdateUtil.setIfNotNull(request.floor(), details::setFloor);
        UpdateUtil.setIfNotNull(request.elevator(), details::setElevator);
        UpdateUtil.setIfNotNull(request.ramp(), details::setRamp);
        UpdateUtil.setIfNotNull(request.side(), details::setSide);
    }

    public void updateCommercialPhysicalDetails(CommercialPhysicalDetails details, CommercialPhysicalDetailsRequest request){
        UpdateUtil.setIfNotNull(request.floor(), details::setFloor);
        UpdateUtil.setIfNotNull(request.line(), details::setLine);
        UpdateUtil.setIfNotNull(request.propertyLocationType(), details::setPropertyLocationType);
        UpdateUtil.setIfNotNull(request.territorialZone(), details::setTerritorialZone);
        UpdateUtil.setIfNotNull(request.separateEntrance(), details::setSeparateEntrance);
        UpdateUtil.setIfNotNull(request.ventilation(), details::setVentilation);
        UpdateUtil.setIfNotNull(request.tenantExists(), details::setTenantExists);
        UpdateUtil.setIfNotNull(request.entrancesNumber(), details::setEntrancesNumber);
        UpdateUtil.setIfNotNull(request.electricalPowerKw(), details::setElectricalPowerKw);
        UpdateUtil.setIfNotNull(request.railwayDeadEnd(), details::setRailwayDeadEnd);
    }

    public void updateCommonLandDetails(CommonLandDetails details, CommonLandDetailsRequest request, LandUse landUse){
        UpdateUtil.setIfNotNull(landUse, details::setLandUse);
        UpdateUtil.setIfNotNull(request.road(), details::setRoad);
        UpdateUtil.setIfNotNull(request.fencing(), details::setFencing);
    }

}
