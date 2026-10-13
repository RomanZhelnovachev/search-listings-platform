package ru.romzheln.data_generator.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.romzheln.data_generator.dto.PercentDto;
import ru.romzheln.data_generator.dto.request.GenerateDataRequest;
import ru.romzheln.data_generator.dto.request.GenerateRequestsRequest;
import ru.romzheln.data_generator.enums.CommunicationType;
import ru.romzheln.data_generator.enums.LandUse;
import ru.romzheln.data_generator.enums.PropertyType;
import ru.romzheln.data_generator.enums.Purpose;
import ru.romzheln.data_generator.exception.AuthorisationException;
import ru.romzheln.data_generator.exception.UnsupportedPropertyException;
import ru.romzheln.data_generator.httpClient.AuthClient;
import ru.romzheln.data_generator.httpClient.ListingClient;
import ru.romzheln.data_generator.httpClient.SearchClient;
import ru.romzheln.data_generator.util.ExecutorUtil;

@Service
@RequiredArgsConstructor
public class GeneratorServiseImpl implements GeneratorService{

    private final AuthClient authClient;
    private final ListingClient listingClient;
    private final SearchClient searchClient;
    private final ResponseGenerator responseGenerator;
    private final RequestGenerator requestGenerator;

    private static final int POOL_SIZE = 10;
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(POOL_SIZE);
    private static final int DEVELOPER_PERCENT = 20;
    private static final int COMPLEX_PERCENT = 30;
    private static final int ADDITIONAL_BUILDING_PERCENT = 30;
    private static final String APARTMENT = "apartment";
    private static final String COMMERCIAL = "commercial";
    private static final String HOUSE = "house";
    private static final String LAND_PLOT = "LandPlot";
    private static final Pageable PAGEABLE = PageRequest.of(0, 20);

    @Override
    public void dataGenerate(GenerateDataRequest request) {
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
        int purposeCount = Purpose.values().length;

        generateDeveloper(developerCount, token);
        generateResidentialComplex(complexCount, token);
        generateAdditionalBuilding(additionalBuildingCount, token);
        generateCommunication(token);
        generateLandUse(token);
        generatePurpose(token);
        generateApartment(apartmentCount, token, (long) developerCount, (long) complexCount, communicationCount);
        generateCommercial(commercialCount, token, purposeCount, communicationCount);
        generateHouse(houseCount, token, (long) landUseCount,(long) developerCount, (long) complexCount, additionalBuildingCount, communicationCount);
        generateLandPlot(landPlotCount, token, (long) landUseCount, additionalBuildingCount, communicationCount);
        generateListing(apartmentCount, commercialCount, houseCount, landPlotCount, token);
    }

    @Override
    public void requestGenerate(GenerateRequestsRequest request) {
        int count = request.count();
    for (int i = 0; i < count; i++) {
      generateApartmentSearchRequest(PAGEABLE);
      generateCommercialSearchRequest(PAGEABLE);
      generateHouseSearchRequest(PAGEABLE);
      generateLandPlotSearchRequest(PAGEABLE);
    }
  }

  private void generateApartmentSearchRequest(Pageable pageable) {
        searchClient.createSearchRequest(requestGenerator.getRegion(), requestGenerator.getApartmentParams(), pageable, PropertyType.APARTMENT);
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
        ExecutorUtil.execute(developers, POOL_SIZE, EXECUTOR, () -> listingClient.createDeveloper(responseGenerator.getDeveloperResponse(), token));
    }

    private void generateResidentialComplex(int complexes, String token){
        ExecutorUtil.execute(complexes, POOL_SIZE, EXECUTOR, ()-> listingClient.createResidentialComplex(responseGenerator.getComplexResponse(), token));
    }

    private void generateAdditionalBuilding(int buildings, String token){
        ExecutorUtil.execute(buildings, POOL_SIZE, EXECUTOR, ()-> listingClient.createAdditionalBuilding(responseGenerator.getAdditionalBuildingResponse(), token));
    }

    private void generateCommunication(String token){
    for(CommunicationType type : CommunicationType.values()){
        listingClient.createCommunication(responseGenerator.getCommunicationResponse(type), token);
    }
    }

    private void generateLandUse(String token) {
        for (LandUse landUse : LandUse.values()) {
            listingClient.createLandUse(responseGenerator.getLandUseResponse(landUse.name()), token);
        }
    }

    private void generatePurpose(String token){
        for(Purpose purpose : Purpose.values()){
            listingClient.createPurpose(responseGenerator.getPurposeResponse(purpose.name()), token);
        }
    }

    private void generateApartment(int apartments, String token, Long maxDeveloperId, Long maxComplexId, int communicationCount){
        ExecutorUtil.execute(apartments, POOL_SIZE, EXECUTOR, ()-> listingClient.createProperty(responseGenerator.getApartmentResponse(maxDeveloperId, maxComplexId, communicationCount), token));
    }

    private void generateCommercial(int commercials, String token, int purposeCount, int communicationCount){
        ExecutorUtil.execute(commercials, POOL_SIZE, EXECUTOR, ()-> listingClient.createProperty(responseGenerator.getCommercialResponse(purposeCount, communicationCount), token));
    }

    private void generateHouse(int houses, String token, Long maxLandUseId, Long maxDeveloperId, Long maxComplexId, int additionalBuildingCount, int communicationCount){
        ExecutorUtil.execute(houses, POOL_SIZE, EXECUTOR, ()-> listingClient.createProperty(responseGenerator.getHouseResponse(maxLandUseId, maxDeveloperId, maxComplexId, additionalBuildingCount, communicationCount), token));
    }

    private void generateLandPlot(int landPlots, String token, Long maxLandUseId, int additionalBuildingCount, int communicationCount){
        ExecutorUtil.execute(landPlots, POOL_SIZE, EXECUTOR, ()-> listingClient.createProperty(responseGenerator.getLandPlotResponse(maxLandUseId, additionalBuildingCount, communicationCount), token));
    }

    private void generateListing(long apartmentCount,
                                 long commercialCount,
                                 long houseCount,
                                 long landPlotCount, String token){
        ExecutorUtil.execute((int) apartmentCount, POOL_SIZE, EXECUTOR, ()-> listingClient.createListing(responseGenerator.getListingResponse(1L, apartmentCount), token));

        ExecutorUtil.execute((int) commercialCount, POOL_SIZE, EXECUTOR, ()-> listingClient.createListing(responseGenerator.getListingResponse(apartmentCount +1, apartmentCount + commercialCount), token));

        ExecutorUtil.execute((int) houseCount, POOL_SIZE, EXECUTOR, ()-> listingClient.createListing(responseGenerator.getListingResponse(apartmentCount + commercialCount + 1, apartmentCount + commercialCount + houseCount), token));

        ExecutorUtil.execute((int) landPlotCount, POOL_SIZE, EXECUTOR, ()-> listingClient.createListing(responseGenerator.getListingResponse(apartmentCount + commercialCount + houseCount + 1, apartmentCount + commercialCount + houseCount + landPlotCount), token));
    }
}
