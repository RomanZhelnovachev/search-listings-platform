package ru.romzheln.data_generator.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.romzheln.data_generator.dto.PercentDto;
import ru.romzheln.data_generator.dto.request.GenerateRequest;
import ru.romzheln.data_generator.enums.CommunicationType;
import ru.romzheln.data_generator.enums.LandUse;
import ru.romzheln.data_generator.exception.AuthorisationException;
import ru.romzheln.data_generator.exception.UnsupportedPropertyException;
import ru.romzheln.data_generator.httpClient.AuthClient;
import ru.romzheln.data_generator.httpClient.ListingClient;
import ru.romzheln.data_generator.util.ExecutorUtil;

@Service
@RequiredArgsConstructor
public class GeneratorServiseImpl implements GeneratorService{

    private final AuthClient authClient;
    private final ListingClient listingClient;
    private final ResponseGenerator generator;        

    private static final int POOL_SIZE = 10;
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(POOL_SIZE);
    private static final int DEVELOPER_PERCENT = 20;
    private static final int COMPLEX_PERCENT = 30;
    private static final int ADDITIONAL_BUILDING_PERCENT = 30;
    private static final String APARTMENT = "apartment";
    private static final String COMMERCIAL = "commercial";
    private static final String HOUSE = "house";
    private static final String LAND_PLOT = "LandPlot";

    @Override
    public void dataGenerate(GenerateRequest request) {
        String token = authClient.getToken();
        if(token == null || token.isBlank()){
            throw new AuthorisationException("Не удалось получить токен");
        }
        int apartmentCount = getPropertyCount(request.total(), request.percents(), APARTMENT);
        int commercialCount = getPropertyCount(request.total(), request.percents(), COMMERCIAL);
        int houseCount = getPropertyCount(request.total(), request.percents(), HOUSE);
        int landPlotCount = getPropertyCount(request.total(), request.percents(), LAND_PLOT);
        int developerCount = getDeveloperCount(apartmentCount);
        int complexCount = getComplexCount(apartmentCount);
        int additionalBuildingCount = getAdditionalBuildingCount(houseCount);
        int communicationCount = CommunicationType.values().length;
        int landUseCount = LandUse.values().length;
        int purposeCount = getPurposeCount(commercialCount);

        generateDeveloper(developerCount, token);
        generateResidentialComplex(complexCount, token);
        generateAdditionalBuilding(additionalBuildingCount, token);
        generateCommunication(communicationCount, token);
        generateLandUse(landUseCount, token);
        generatePurpose(purposeCount, token);
        generateApartment(apartmentCount, token, (long) developerCount, (long) complexCount, communicationCount);
        generateCommercial(commercialCount, token, purposeCount, communicationCount);
        generateHouse(houseCount, token, (long) landUseCount,(long) developerCount, (long) complexCount, additionalBuildingCount, communicationCount);
        generateLandPlot(landPlotCount, token, (long) landUseCount, additionalBuildingCount, communicationCount);
        generateListing(request.total(), token);
    }

    private int getDeveloperCount(int apartmentCount) {
        int count = apartmentCount * DEVELOPER_PERCENT / 100;
        return Math.max(count, 1);
    }

    private int getComplexCount(int apartmentCount) {
        int count = apartmentCount * COMPLEX_PERCENT / 100;
        return Math.max(count, 1);
    }

    private int getAdditionalBuildingCount(int houseCount) {
        int count = houseCount * ADDITIONAL_BUILDING_PERCENT / 100;
        return Math.max(count, 1);
    }

    private int getPurposeCount(int commercialCount) {
        int count = commercialCount * COMPLEX_PERCENT / 100;
        return Math.max(count, 1);
    }

    private int getPropertyCount(int total, PercentDto dto, String property){
        Integer propertyPercent;
        if(dto == null){
            propertyPercent = 25;
        } else {
        switch (property){
            case APARTMENT -> propertyPercent = dto.apartmentPercent();
            case COMMERCIAL -> propertyPercent = dto.commercialPercent();
            case HOUSE -> propertyPercent = dto.housePercent();
            case LAND_PLOT -> propertyPercent = dto.landPlotPercent();
            default -> throw new UnsupportedPropertyException("Неподдерживаемый тип объекта недвижимости " + property);
        }
    }
        return propertyPercent == null ? 0 : total * propertyPercent / 100;
        }

    private void generateDeveloper(int developers, String token){
        ExecutorUtil.execute(developers, POOL_SIZE, EXECUTOR, () -> listingClient.createDeveloper(generator.getDeveloperResponse(), token));
    }

    private void generateResidentialComplex(int complexes, String token){
        ExecutorUtil.execute(complexes, POOL_SIZE, EXECUTOR, ()-> listingClient.createResidentialComplex(generator.getComplexResponse(), token));
    }

    private void generateAdditionalBuilding(int buildings, String token){
        ExecutorUtil.execute(buildings, POOL_SIZE, EXECUTOR, ()-> listingClient.createAdditionalBuilding(generator.getAdditionalBuildingResponse(), token));
    }

    private void generateCommunication(int communications, String token){
    ExecutorUtil.execute(communications, POOL_SIZE, EXECUTOR, () -> listingClient.createCommunication(generator.getCommunicationResponse(), token));
    }

    private void generateLandUse(int landUses, String token){
        ExecutorUtil.execute(landUses, POOL_SIZE, EXECUTOR, () -> listingClient.createLandUse(generator.getLandUseResponse(), token));
    }

    private void generatePurpose(int purposes, String token){
        ExecutorUtil.execute(purposes, POOL_SIZE, EXECUTOR, () -> listingClient.createPurpose(generator.getPurposeResponse(), token));
    }

    private void generateApartment(int apartments, String token, Long maxDeveloperId, Long maxComplexId, int communicationCount){
        ExecutorUtil.execute(apartments, POOL_SIZE, EXECUTOR, ()-> listingClient.createProperty(generator.getApartmentResponse(maxDeveloperId, maxComplexId, communicationCount), token));
    }

    private void generateCommercial(int commercials, String token, int purposeCount, int communicationCount){
        ExecutorUtil.execute(commercials, POOL_SIZE, EXECUTOR, ()-> listingClient.createProperty(generator.getCommercialResponse(purposeCount, communicationCount), token));
    }

    private void generateHouse(int houses, String token, Long maxLandUseId, Long maxDeveloperId, Long maxComplexId, int additionalBuildingCount, int communicationCount){
        ExecutorUtil.execute(houses, POOL_SIZE, EXECUTOR, ()-> listingClient.createProperty(generator.getHouseResponse(maxLandUseId, maxDeveloperId, maxComplexId, additionalBuildingCount, communicationCount), token));
    }

    private void generateLandPlot(int landPlots, String token, Long maxLandUseId, int additionalBuildingCount, int communicationCount){
        ExecutorUtil.execute(landPlots, POOL_SIZE, EXECUTOR, ()-> listingClient.createProperty(generator.getLandPlotResponse(maxLandUseId, additionalBuildingCount, communicationCount), token));
    }

    private void generateListing(int total, String token){
        ExecutorUtil.execute(total, POOL_SIZE, EXECUTOR, ()-> listingClient.createListing(generator.getListingResponse((long) total), token));
    }
}
