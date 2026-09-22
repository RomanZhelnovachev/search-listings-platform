package ru.romzheln.search_service.factory;

import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.common.*;
import ru.romzheln.search_service.dto.event.listing.ListingCreatedEvent;
import ru.romzheln.search_service.dto.event.property.*;
import ru.romzheln.search_service.model.embeded.*;
import ru.romzheln.search_service.model.enums.*;

import java.util.HashSet;

@Component
public class EmbeddedFactory {

    public Apartment getApartment(ApartmentEvent event) {
        CommonPhysicalDetailsDto commonPhysicalDetailsDto = event.getCommonPhysicalDetailsDto();
        ApartmentPhysicalDetailsDto apartmentPhysicalDetailsDto =
                event.getApartmentPhysicalDetailsDto();
        return Apartment.builder()
                .apartmentType(ApartmentType.valueOf(event.getApartmentType()))
                .commonPhysicalDetails(getCommonPhysicalDetails(commonPhysicalDetailsDto))
                .apartmentPhysicalDetails(getApartmentPhysicalDetails(apartmentPhysicalDetailsDto))
                .developer(new Developer(event.getDeveloperId(), event.getDeveloperName()))
                .complex(new ResidentialComplex(event.getComplexId(), event.getComplexName()))
                .build();
    }

    public ApartmentPhysicalDetails getApartmentPhysicalDetails(ApartmentPhysicalDetailsDto dto) {
        return ApartmentPhysicalDetails.builder()
                .kitchenSquare(dto.kitchenSquare())
                .floor(dto.floor())
                .elevator(Elevator.valueOf(dto.elevator()))
                .ramp(Ramp.valueOf(dto.ramp()))
                .side(Side.valueOf(dto.side()))
                .build();
    }

    public CommonPhysicalDetails getCommonPhysicalDetails(CommonPhysicalDetailsDto dto) {
        return CommonPhysicalDetails.builder()
                .roomsNumber(dto.roomsNumber())
                .ceilingHeight(dto.ceilingHeight())
                .renovation(Renovation.valueOf(dto.renovation()))
                .bathroom(Bathroom.valueOf(dto.bathroom()))
                .material(WallMaterial.valueOf(dto.material()))
                .completionDate(dto.completionDate())
                .yearBuilt(dto.yearBuilt())
                .floorsNumber(dto.floorsNumber())
                .view(WindowView.valueOf(dto.view()))
                .balcony(Balcony.valueOf(dto.balcony()))
                .windowType(WindowType.valueOf(dto.windowType()))
                .windowMaterial(WindowMaterial.valueOf(dto.windowMaterial()))
                .layoutFeature(LayoutFeature.valueOf(dto.layoutFeature()))
                .layoutType(LayoutType.valueOf(dto.layoutType()))
                .build();
    }

    public Listing getListing(ListingCreatedEvent listingEvent, PropertyEvent propertyEvent) {
        return Listing.builder()
                .title(listingEvent.title())
                .description(listingEvent.description())
                .imageIds(new HashSet<>())
                .ownerId(listingEvent.ownerId())
                .propertyId(listingEvent.propertyId())
                .property(getProperty(propertyEvent))
                .dealType(DealType.valueOf(listingEvent.dealType()))
                .price(listingEvent.price())
                .mortgageProgramIds(new HashSet<>())
                .promotionId(null)
                .build();
    }

    public Property getProperty(PropertyEvent propertyEvent) {
        LocationDto location = propertyEvent.getLocation();
        return Property.builder()
                .location(getLocation(location))
                .square(propertyEvent.getSquare())
                .own(Own.valueOf(propertyEvent.getOwn()))
                .firstOwner(propertyEvent.getFirstOwner())
                .communicationIds(propertyEvent.getCommunicationIds())
                .build();
    }

    public Location getLocation(LocationDto location) {
        return Location.builder()
                .region(Region.valueOf(location.region()))
                .populatedArea(location.populatedArea())
                .street(location.street())
                .house(location.house())
                .building(location.building())
                .apartment(location.apartment())
                .build();
    }

    public Commercial getCommercial(CommercialEvent event) {
        CommonPhysicalDetailsDto commonPhysicalDetailsDto = event.getCommonPhysicalDetailsDto();
        CommercialPhysicalDetailsDto commercialPhysicalDetailsDto =
                event.getCommercialPhysicalDetailsDto();
        return Commercial.builder()
                .commonPhysicalDetails(getCommonPhysicalDetails(commonPhysicalDetailsDto))
                .commercialPhysicalDetails(getCommercialPhysicalDetails(commercialPhysicalDetailsDto))
                .purposeIds(event.getPurposesIds())
                .build();
    }

    public LandPlot getLandPlot(LandPlotEvent event) {
        CommonLandDetailsDto commonLandDetailsDto = event.getCommonLandDetailsDto();
        return LandPlot.builder()
                .commonLandDetails(getCommonLandDetails(commonLandDetailsDto))
                .additionalBuildings(event.getAdditionalBuildings())
                .build();
    }

    public CommercialPhysicalDetails getCommercialPhysicalDetails(CommercialPhysicalDetailsDto dto) {
        return CommercialPhysicalDetails.builder()
                .floor(dto.floor())
                .line(Line.valueOf(dto.line()))
                .propertyLocationType(PropertyLocationType.valueOf(dto.propertyLocationType()))
                .territorialZone(TerritorialZone.valueOf(dto.territorialZone()))
                .separateEntrance(dto.separateEntrance())
                .ventilation(dto.ventilation())
                .tenantExists(dto.tenantExists())
                .entrancesNumber(dto.entrancesNumber())
                .electricalPowerKw(dto.electricalPowerKw())
                .railwayDeadEnd(dto.railwayDeadEnd())
                .build();
    }

    public House getHouse(HouseEvent event) {
        CommonPhysicalDetailsDto commonPhysicalDetailsDto = event.getCommonPhysicalDetailsDto();
        CommonLandDetailsDto commonLandDetailsDto = event.getCommonLandDetailsDto();
        return House.builder()
                .commonPhysicalDetails(getCommonPhysicalDetails(commonPhysicalDetailsDto))
                .commonLandDetails(getCommonLandDetails(commonLandDetailsDto))
                .developer(new Developer(event.getDeveloperId(), event.getDeveloperName()))
                .complex(new ResidentialComplex(event.getComplexId(), event.getComplexName()))
                .constructionStage(ConstructionStage.valueOf(event.getConstructionStage()))
                .additionalBuildings(event.getAdditionalBuildings())
                .landPlotSquare(event.getLandPlotSquare())
                .build();
    }

    public CommonLandDetails getCommonLandDetails(CommonLandDetailsDto dto) {
        return CommonLandDetails.builder()
                .landUseId(dto.landUse())
                .landUseName(dto.landUseName())
                .road(dto.road())
                .fencing(dto.fencing())
                .build();
    }

    public ListingKey getListingKey(Long listingId, String region) {
        return new ListingKey(listingId, Region.valueOf(region));
    }
}
