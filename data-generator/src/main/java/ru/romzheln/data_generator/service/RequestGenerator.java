package ru.romzheln.data_generator.service;

import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;
import ru.romzheln.data_generator.dto.criteria.CommonSearchCriteria;
import ru.romzheln.data_generator.dto.request.ApartmentSearchRequest;
import ru.romzheln.data_generator.enums.Own;
import ru.romzheln.data_generator.enums.Region;

import java.util.*;

@Component
@RequiredArgsConstructor
public class RequestGenerator {

    private final Faker faker;

    private static final double PERCENT_NULL = 0.3;
    private static final Random random = new Random();

    public String getRegion(){
        Region region = faker.options().option(Region.class);
        return region.getUrl();
    }

    public Map<String, List<String>> getApartmentParams(){
        Map<String, List<String>> params = new HashMap<>();
        var request = getApartmentSearchRequest();
        return params;
    }

    private ApartmentSearchRequest getApartmentSearchRequest(){
        return ApartmentSearchRequest.builder()
                .commonSearchCriteria(nullable(generateCommonSearchCriteria()))
                .apartmentTypes()
                .commonPhysicalCriteria()
                .apartmentPhysicalCriteria()
                .developerIds()
                .complexIds()
                .build();
    }

    private CommonSearchCriteria generateCommonSearchCriteria(){

        return CommonSearchCriteria.builder()
                .hasImage(nullable(faker.bool().bool()))
                .owns(nullable(getSetEnum(Own.class)))
                .squareFrom()
                .squareTo()
                .firstOwner()
                .dealTypes()
                .priceFrom()
                .priceTo()
                .mortgageProgramIds()
                .promotionId()
                .build();
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
}
