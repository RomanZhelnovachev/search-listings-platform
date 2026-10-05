package ru.romzheln.data_generator.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;
import ru.romzheln.data_generator.dto.*;
import ru.romzheln.data_generator.dto.response.*;
import ru.romzheln.data_generator.enums.*;

@Component
@RequiredArgsConstructor
public class ResponseGenerator {

    private final Faker faker;

    public DeveloperResponse getDeveloperResponse(){
      return new DeveloperResponse(faker.company().name());
    }

    public ResidentialComplexResponse getComplexResponse(){
        return new ResidentialComplexResponse("ЖК " + faker.company().name());
    }

    public AdditionalBuildingResponse getAdditionalBuildingResponse(){
        String name = generateAdditionalBuildingName();
        return new AdditionalBuildingResponse(name, "Просто " + name);
    }

    public CommunicationResponse getCommunicationResponse(){
        return new CommunicationResponse(generateCommunicationType(), "Описание отсутствует");
    }

    public LandUseResponse getLandUseResponse(){
    return new LandUseResponse(generateLandUseName(), "-");
    }

    public PurposeResponse getPurposeResponse(){
    return new PurposeResponse(generatePurposeName(), "Какое-то описание");
    }

    public CreateApartmentResponse getApartmentResponse(Long maxDeveloperId, Long maxComplexId, int communicationCount){
        var apartmentType = faker.options().option(ApartmentType.class);
        var commonPhysicalDetails = generateCommonPhysicalDetails();
        var apartment = new CreateApartmentResponse(apartmentType, commonPhysicalDetails, generateApartmentPhysicalDetails(commonPhysicalDetails.floorsNumber()), faker.number().numberBetween(1, maxDeveloperId), faker.number().numberBetween(1, maxComplexId));
        setGeneralField(apartment, PropertyType.APARTMENT, commonPhysicalDetails.floorsNumber(),communicationCount);
        return apartment;
    }

    public CreateCommercialResponse getCommercialResponse(int purposeCount, int communicationCount){
        var commonPhysicalDetails = generateCommonPhysicalDetails();
        var commercial = new CreateCommercialResponse(commonPhysicalDetails, generateCommercialPhysicalDetails(), generateSetLong(purposeCount));
        setGeneralField(commercial, PropertyType.COMMERCIAL, commonPhysicalDetails.floorsNumber(), communicationCount);
        return commercial;
    }

    public CreateHouseResponse getHouseResponse(Long maxLandUseId, Long maxDeveloperId, Long maxComplexId, int additionalBuildingCount, int communicationCount){
        var commonPhysicalDetails = generateCommonPhysicalDetails();
        var house = new CreateHouseResponse(commonPhysicalDetails, generateCommonLandDetails(maxLandUseId), faker.number().numberBetween(1, maxDeveloperId), faker.number().numberBetween(1, maxComplexId), faker.options().option(ConstructionStage.class), generateSetLong(additionalBuildingCount),
                BigDecimal.valueOf(faker.number().randomDouble(2, 0, 300)));
        setGeneralField(house, PropertyType.HOUSE, commonPhysicalDetails.floorsNumber(), communicationCount);
        return house;
    }

    public CreateLandPlotResponse getLandPlotResponse(Long maxLandUseId, int additionalBuildingCount, int communicationCount){
        var landPlot = new CreateLandPlotResponse(generateCommonLandDetails(maxLandUseId), generateSetLong(additionalBuildingCount));
        setGeneralField(landPlot, PropertyType.LAND_PLOT, 1, communicationCount);
        return landPlot;
    }

    public CreateListingResponse getListingResponse(Long maxPropertyId){
        String title = faker.commerce().productName() + " - " +
                faker.options().option("Продажа", "Срочная продажа", "Новое");
        return new CreateListingResponse(title, "Лучшее предложение " + title, faker.number().numberBetween(1L, 1000), faker.number().numberBetween(1, maxPropertyId), faker.options().option(DealType.class), BigDecimal.valueOf(faker.number().randomDouble(2, 500000, 35000000)));
    }

    private String generateAdditionalBuildingName() {
        var building = faker.options().option(AdditionalBuilding.class);
        return building.name();
    }

    private CommunicationType generateCommunicationType(){
        return faker.options().option(CommunicationType.class);
    }

    private String generateLandUseName() {
        var landUse = faker.options().option(LandUse.class);
        return landUse.name();
    }

    private String generatePurposeName() {
        var purpose = faker.options().option(Purpose.class);
        return purpose.name();
    }

    private CommonPhysicalDetailsDto generateCommonPhysicalDetails(){
        return CommonPhysicalDetailsDto.builder()
                .roomsNumber(faker.number().numberBetween(1, 5))
                .ceilingHeight(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 3)))
                .renovation(faker.options().option(Renovation.class))
                .bathroom(faker.options().option(Bathroom.class))
                .material(faker.options().option(WallMaterial.class))
                .completionDate(faker.timeAndDate().between(LocalDate.of(2010, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant(),
                        LocalDate.of(2030, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()).atZone(ZoneId.systemDefault()).toLocalDate())
                .yearBuilt(faker.number().numberBetween(1950, 2026))
                .floorsNumber(faker.number().numberBetween(1, 20))
                .view(faker.options().option(WindowView.class))
                .balcony(faker.options().option(Balcony.class))
                .windowType(faker.options().option(WindowType.class))
                .windowMaterial(faker.options().option(WindowMaterial.class))
                .layoutFeature(faker.options().option(LayoutFeature.class))
                .layoutType(faker.options().option(LayoutType.class))
                .build();
    }

    private ApartmentPhysicalDetailsDto generateApartmentPhysicalDetails(int maxFloor){
        return ApartmentPhysicalDetailsDto.builder()
                .kitchenSquare(BigDecimal.valueOf(faker.number().randomDouble(2, 5, 20)))
                .floor(faker.number().numberBetween(1, maxFloor))
                .elevator(faker.options().option(Elevator.class))
                .ramp(faker.options().option(Ramp.class))
                .side(faker.options().option(Side.class))
                .build();
    }

    private CommercialPhysicalDetailsDto generateCommercialPhysicalDetails(){
        return CommercialPhysicalDetailsDto.builder()
                .floor(faker.number().numberBetween(0, 20))
                .line(faker.options().option(Line.class))
                .propertyLocationType(faker.options().option(PropertyLocationType.class))
                .territorialZone(faker.options().option(TerritorialZone.class))
                .separateEntrance(faker.bool().bool())
                .ventilation(faker.bool().bool())
                .tenantExists(faker.bool().bool())
                .entrancesNumber(faker.number().numberBetween(1, 5))
                .electricalPowerKw(faker.number().numberBetween(2, 20))
                .railwayDeadEnd(faker.bool().bool())
                .build();
    }

    private void setGeneralField(CreatePropertyResponse response, PropertyType type, int floors, int communicationCount){
        response.setPropertyType(type);
        response.setLocation(generateLocation(floors));
        response.setSquare(BigDecimal.valueOf(faker.number().randomDouble(2, 19, 120)));
        response.setOwn(faker.options().option(Own.class));
        response.setFirstOwner(faker.bool().bool());
        response.setCommunicationIds(generateSetLong(communicationCount));
    }

    private LocationDto generateLocation(int floors){
        return LocationDto.builder()
                .region(faker.options().option(Region.class))
                .populatedArea(faker.address().cityName())
                .street(faker.address().streetName())
                .house(String.valueOf(faker.number().numberBetween(1, 300)))
                .building(faker.letterify("?").toUpperCase())
                .apartment(String.valueOf(faker.number().numberBetween(1, floors * 10)))
                .build();
    }

    private Set<Long> generateSetLong(int maxCount){
        Set<Long> result = new HashSet<>();
        int count = faker.number().numberBetween(1, maxCount);
        for(int i = 0; i < count; i++) {
            Long id = faker.number().numberBetween(1, (long) maxCount);
            result.add(id);
        }
        return result;
    }

    private CommonLandDetailsDto generateCommonLandDetails(Long maxLandUseId){
        return CommonLandDetailsDto.builder()
                .landUse(faker.number().numberBetween(1, maxLandUseId))
                .road(faker.options().option(Road.class).name())
                .fencing(faker.options().option(Fencing.class).name())
                .build();
    }
}
