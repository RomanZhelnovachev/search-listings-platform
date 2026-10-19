package ru.romzheln.data_generator.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;
import ru.romzheln.data_generator.dto.criteria.*;
import ru.romzheln.data_generator.dto.request.ApartmentSearchRequest;
import ru.romzheln.data_generator.dto.request.CommercialSearchRequest;
import ru.romzheln.data_generator.dto.request.HouseSearchRequest;
import ru.romzheln.data_generator.dto.request.LandPlotSearchRequest;
import ru.romzheln.data_generator.enums.*;
import ru.romzheln.data_generator.mapper.SearchStringMapper;

import static ru.romzheln.data_generator.util.MapperUtil.putSetToMap;

@Component
@RequiredArgsConstructor
public class RequestGenerator {

    private final Faker faker;
    private final SearchStringMapper mapper;

    private static final double PERCENT_NULL = 0.3;
    private static final Long MAX_ID = 100L;
    private static final Random random = new Random();

    public String getRegion(){
        Region region = faker.options().option(Region.class);
        return region.getUrl();
    }

    public Map<String, List<String>> getApartmentParams(ApartmentSearchRequest request){
       return mapper.mapFromApartmentSearchRequest(request);
    }

    public Map<String, List<String>> getCommercialParams(CommercialSearchRequest request){
        return mapper.mapFromCommercialSearchRequest(request);
    }

    public Map<String, List<String>> getHouseParams(HouseSearchRequest request){
        return mapper.mapFromHouseSearchRequest(request);
    }

    public Map<String, List<String>> getLandPlotParams(LandPlotSearchRequest request){
        return mapper.mapFromLandPlotSearchRequest(request);
    }

    public ApartmentSearchRequest getApartmentSearchRequest(){
        return ApartmentSearchRequest.builder()
                .commonSearchCriteria(nullable(generateCommonSearchCriteria()))
                .apartmentTypes(nullable(getSetEnum(ApartmentType.class)))
                .commonPhysicalCriteria(nullable(generateCommonPhysicalCriteria()))
                .apartmentPhysicalCriteria(nullable(generateApartmentPhysicalCriteria()))
                .developerIds(nullable(getSetLong(MAX_ID)))
                .complexIds(nullable(getSetLong(MAX_ID)))
                .build();
    }

    public CommercialSearchRequest getCommercialSearchRequest(){
        return CommercialSearchRequest.builder()
                .commonSearchCriteria(nullable(generateCommonSearchCriteria()))
                .commonPhysicalCriteria(nullable(generateCommonPhysicalCriteria()))
                .commercialPhysicalCriteria(nullable(generateCommercialPhysicalCriteria()))
                .purposeIds(nullable(getSetLong(MAX_ID)))
                .build();
    }

    public HouseSearchRequest getHouseSearchRequest(){
        BigDecimal landPlotSquareFrom = nullable(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100)));
        Integer min = landPlotSquareFrom == null ? null : landPlotSquareFrom.intValue();
        return HouseSearchRequest.builder()
               .commonSearchCriteria(nullable(generateCommonSearchCriteria()))
               .commonPhysicalCriteria(nullable(generateCommonPhysicalCriteria()))
               .commonLandCriteria(nullable(generateCommonLandCriteria()))
               .developerIds(nullable(getSetLong(MAX_ID)))
               .complexIds(nullable(getSetLong(MAX_ID)))
               .constructionStages(nullable(getSetEnum(ConstructionStage.class)))
               .additionalBuildingIds(nullable(getSetLong(MAX_ID)))
               .landPlotSquareFrom(landPlotSquareFrom)
               .landPlotSquareTo(nullable(getBigDecimalTo(min, 1000)))
               .build();
    }
    
    public LandPlotSearchRequest getLandPlotSearchRequest(){
        return LandPlotSearchRequest.builder()
                .commonSearchCriteria(nullable(generateCommonSearchCriteria()))
                .commonLandCriteria(nullable(generateCommonLandCriteria()))
                .additionalBuildingIds(nullable(getSetLong(MAX_ID)))
                .build();
    }

    private CommonLandCriteria generateCommonLandCriteria(){
        return CommonLandCriteria.builder()
                .landUseIds(nullable(getSetLong(MAX_ID)))
                .build();
    }

    private CommonSearchCriteria generateCommonSearchCriteria(){
        BigDecimal squareFrom = nullable(BigDecimal.valueOf(faker.number().randomDouble(2, 15, 80)));
        Integer min = squareFrom == null ? null : squareFrom.intValue();
        BigDecimal priceFrom = nullable(BigDecimal.valueOf(faker.number().randomDouble(2, 0, 500000)));
        Integer minPrice = priceFrom == null ? null : priceFrom.intValue();
        return CommonSearchCriteria.builder()
                .hasImage(nullable(faker.bool().bool()))
                .owns(nullable(getSetEnum(Own.class)))
                .squareFrom(squareFrom)
                .squareTo(nullable(getBigDecimalTo(min, 300)))
                .firstOwner(nullable(faker.bool().bool()))
                .dealTypes(nullable(getSetEnum(DealType.class)))
                .priceFrom(priceFrom)
                .priceTo(nullable(getBigDecimalTo(minPrice, 50000000)))
                .mortgageProgramIds(nullable(getSetLong(MAX_ID)))
                .promotionId(nullable(faker.number().numberBetween(1, MAX_ID)))
                .build();
    }

    private CommonPhysicalCriteria generateCommonPhysicalCriteria(){
        BigDecimal ceilingHeightFrom = nullable(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 3)));
        Integer min = ceilingHeightFrom == null ? null : ceilingHeightFrom.intValue();
        Integer yearBuiltFrom = faker.number().numberBetween(1950, 2026);
        Integer floorsNumberFrom = faker.number().numberBetween(1, 20);
        return CommonPhysicalCriteria.builder()
                .roomsNumber(nullable(getSetInteger(5)))
                .ceilingHeightFrom(ceilingHeightFrom)
                .ceilingHeightTo(nullable(getBigDecimalTo(min, 4)))
                .renovations(nullable(getSetEnum(Renovation.class)))
                .bathrooms(nullable(getSetEnum(Bathroom.class)))
                .wallMaterials(nullable(getSetEnum(WallMaterial.class)))
                .completionDate(nullable(faker.timeAndDate().between(LocalDate.of(2010, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant(),
                        LocalDate.of(2030, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()).atZone(ZoneId.systemDefault()).toLocalDate()))
                .yearBuiltFrom(nullable(yearBuiltFrom))
                .yearBuiltTo(nullable(getIntegerTo(yearBuiltFrom, 2035)))
                .floorsNumberFrom(nullable(floorsNumberFrom))
                .floorsNumberTo(nullable(getIntegerTo(floorsNumberFrom, 25)))
                .views(nullable(getSetEnum(WindowView.class)))
                .balconies(nullable(getSetEnum(Balcony.class)))
                .windowTypes(nullable(getSetEnum(WindowType.class)))
                .windowMaterials(nullable(getSetEnum(WindowMaterial.class)))
                .layoutFeatures(nullable(getSetEnum(LayoutFeature.class)))
                .layoutTypes(nullable(getSetEnum(LayoutType.class)))
                .build();
    }

    private ApartmentPhysicalCriteria generateApartmentPhysicalCriteria(){
        BigDecimal kitchenSquareFrom = nullable(BigDecimal.valueOf(faker.number().randomDouble(2, 5, 20)));
        Integer min = kitchenSquareFrom == null ? null : kitchenSquareFrom.intValue();
        Integer floorFrom = faker.number().numberBetween(1, 20);
        return ApartmentPhysicalCriteria.builder()
                .kitchenSquareFrom(kitchenSquareFrom)
                .kitchenSquareTo(nullable(getBigDecimalTo(min, 30)))
                .floorFrom(nullable(floorFrom))
                .floorTo(nullable(getIntegerTo(floorFrom, 25)))
                .elevators(nullable(getSetEnum(Elevator.class)))
                .ramps(nullable(getSetEnum(Ramp.class)))
                .sides(nullable(getSetEnum(Side.class)))
                .build();
    }

    private CommercialPhysicalCriteria generateCommercialPhysicalCriteria(){
        Integer floorFrom = nullable(faker.number().numberBetween(1, 20));
        Integer entrancesNumberFrom = nullable(faker.number().numberBetween(1, 5));
        Integer electricalPowerKwFrom = nullable(faker.number().numberBetween(1, 20));
        return CommercialPhysicalCriteria.builder()
                .floorFrom(floorFrom)
                .floorTo(nullable(getIntegerTo(floorFrom, 25)))
                .lines(nullable(getSetEnum(Line.class)))
                .propertyLocationTypes(nullable(getSetEnum(PropertyLocationType.class)))
                .territorialZones(nullable(getSetEnum(TerritorialZone.class)))
                .separateEntrance(nullable(faker.bool().bool()))
                .ventilation(nullable(faker.bool().bool()))
                .tenantExists(nullable(faker.bool().bool()))
                .entrancesNumberFrom(entrancesNumberFrom)
                .entrancesNumberTo(nullable(getIntegerTo(entrancesNumberFrom, 7)))
                .electricalPowerKwFrom(electricalPowerKwFrom)
                .electricalPowerKwTo(nullable(getIntegerTo(electricalPowerKwFrom, 100)))
                .railwayDeadEnd(nullable(faker.bool().bool()))
                .build();
    }

    private BigDecimal getBigDecimalTo(Integer from, int max){
        int min = from == null ? 0 : from;
        return BigDecimal.valueOf(faker.number().randomDouble(2, min, max));
    }

    private Integer getIntegerTo(Integer from, int max){
        int min = from == null ? 0 : from;
        return faker.number().numberBetween(min, max);
    }

    private <T extends Enum<T>> Set<T> getSetEnum(Class<T> enumClass){
        T[] constants = enumClass.getEnumConstants();
        int count = random.nextInt(1, constants.length);
    Set<T> enums = new HashSet<>();
    for(int i = 0; i < count; i++) {
        var element = faker.options().option(enumClass);
        enums.add(element);
    }
    return enums;
    }

    private <T> T nullable(T value){
        return Math.random() < PERCENT_NULL ? null : value;
    }

    private Set<Long> getSetLong(long maxValue){
    int count = random.nextInt(1, 10);
    Set<Long> ids = new HashSet<>();
    for(int i = 0; i < count; i++) {
        Long id = faker.number().numberBetween(1, maxValue);
        ids.add(id);
    }
    return ids;
  }

  private Set<Integer> getSetInteger(int max){
        int count = random.nextInt(1, max);
        Set<Integer> result = new HashSet<>();
        for(int i = 0; i < count; i++) {
            Integer element = faker.number().numberBetween(1, max);
            result.add(element);
        }
        return result;
  }
}
