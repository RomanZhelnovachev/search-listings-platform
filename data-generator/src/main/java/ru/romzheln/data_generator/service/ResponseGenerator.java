package ru.romzheln.data_generator.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;
import ru.romzheln.data_generator.dto.ApartmentPhysicalDetailsDto;
import ru.romzheln.data_generator.dto.CommonPhysicalDetailsDto;
import ru.romzheln.data_generator.dto.LocationDto;
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

    private void setGeneralField(CreatePropertyResponse response, PropertyType type, int floors, int communicationCount){
        response.setPropertyType(type);
        response.setLocation(generateLocation(floors));
        response.setSquare(BigDecimal.valueOf(faker.number().randomDouble(2, 19, 120)));
        response.setOwn(faker.options().option(Own.class));
        response.setFirstOwner(faker.bool().bool());
        response.setCommunicationIds(generateSetLong(communicationCount, (long)communicationCount));
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

    private Set<Long> generateSetLong(int maxCount, Long maxValue){
        Set<Long> result = new HashSet<>();
        int count = faker.number().numberBetween(1, maxCount);
        for(int i = 0; i < count; i++) {
            Long id = faker.number().numberBetween(1, maxValue);
            result.add(id);
        }
        return result;
    }
}
