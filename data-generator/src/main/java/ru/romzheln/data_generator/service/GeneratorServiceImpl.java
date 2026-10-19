package ru.romzheln.data_generator.service;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.romzheln.data_generator.dto.PercentDto;
import ru.romzheln.data_generator.dto.request.*;
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
@Slf4j
public class GeneratorServiceImpl implements GeneratorService{

    private final AuthClient authClient;
    private final ListingClient listingClient;
    private final SearchClient searchClient;
    private final ResponseGenerator responseGenerator;
    private final RequestGenerator requestGenerator;
    private final DatabaseCleanupService databaseCleanupService;
    private final KafkaCleanupService kafkaCleanupService;

    private final AtomicLong approveId = new AtomicLong(1);
    private final AtomicLong publishId = new AtomicLong(1);
    private final AtomicBoolean operationInProgress = new AtomicBoolean(false);

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
        if (!operationInProgress.compareAndSet(false, true)) {
            throw new IllegalStateException(
                    "Другая операция уже выполняется"
            );
        }
        try{
            String token = authClient.getToken();
        if(token == null || token.isBlank()){
            log.error("Не удалось получить токен");
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
        log.info("Сгенерировано {} застройщиков", developerCount);
        generateResidentialComplex(complexCount, token);
        log.info("Сгенерировано {} жилых комплексов", complexCount);
        generateAdditionalBuilding(additionalBuildingCount, token);
        log.info("Сгенерировано {} дополнительных построек", additionalBuildingCount);
        generateCommunication(token);
        log.info("Сгенерировано {} коммуникаций", communicationCount);
        generateLandUse(token);
        log.info("Сгенерировано {} земельных целей", landUseCount);
        generatePurpose(token);
        log.info("Сгенерировано {} коммерческих целей", purposeCount);
        generateApartment(apartmentCount, token, (long) developerCount, (long) complexCount, communicationCount);
        log.info("Сгенерировано {} квартир", apartmentCount);
        generateCommercial(commercialCount, token, purposeCount, communicationCount);
        log.info("Сгенерировано {} объектов коммерческой недвижимости", commercialCount);
        generateHouse(houseCount, token, (long) landUseCount,(long) developerCount, (long) complexCount, additionalBuildingCount, communicationCount);
        log.info("Сгенерировано {} домов", houseCount);
        generateLandPlot(landPlotCount, token, (long) landUseCount, additionalBuildingCount, communicationCount);
        log.info("Сгенерировано {} земельных участков", landPlotCount);
        generateListing(apartmentCount, commercialCount, houseCount, landPlotCount, token);
        log.info("Сгенерировано {} объявлений", request.total());
        approveListings(request.total(), token);
        publishListings(request.total(), token);
        } finally{
            operationInProgress.set(false);
        }
    }

    @Override
    public void requestGenerate(GenerateRequestsRequest request) {
        if (!operationInProgress.compareAndSet(false, true)) {
            throw new IllegalStateException(
                    "Другая операция уже выполняется"
            );
        }
        try{
            int count = request.count();
    for (int i = 0; i < count; i++) {
      var apartmentRequest = generateApartmentSearchRequest();
      log.info("Сгенерирован запрос на поиск квартир с параметрами {}", apartmentRequest);
      var commercialRequest = generateCommercialSearchRequest();
        log.info("Сгенерирован запрос на поиск коммерческой недвижимости с параметрами {}", commercialRequest);
      var houseRequest = generateHouseSearchRequest();
        log.info("Сгенерирован запрос на поиск домов с параметрами {}", houseRequest);
      var landPlotRequest = generateLandPlotSearchRequest();
        log.info("Сгенерирован запрос на поиск земельных участков с параметрами {}", landPlotRequest);
    }
    } finally{
            operationInProgress.set(false);
        }
  }

    public void cleanupDatabases() {
        if (!operationInProgress.compareAndSet(false, true)) {
            throw new IllegalStateException(
                    "Другая операция уже выполняется"
            );
        }
        try {
            kafkaCleanupService.cleanupTopic();
            databaseCleanupService.clearSearchDatabase();
            databaseCleanupService.clearListingDatabase();
            approveId.set(1);
            publishId.set(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Очистка прервана", e);
        } catch (java.util.concurrent.ExecutionException e) {
            throw new IllegalStateException("Не удалось очистить Kafka", e);
        } finally {
            operationInProgress.set(false);
        }
    }

  private ApartmentSearchRequest generateApartmentSearchRequest() {
        var request = requestGenerator.getApartmentSearchRequest();
      String region = requestGenerator.getRegion();
      log.info("Регион поискового запроса квартиры: {}", region);
        searchClient.createSearchRequest(region, requestGenerator.getApartmentParams(request), PAGEABLE, PropertyType.APARTMENT);
        return request;
    }

    private CommercialSearchRequest generateCommercialSearchRequest() {
        var request = requestGenerator.getCommercialSearchRequest();
        String region = requestGenerator.getRegion();
        log.info("Регион поискового запроса коммерции: {}", region);
        searchClient.createSearchRequest(region, requestGenerator.getCommercialParams(request), PAGEABLE, PropertyType.COMMERCIAL);
        return request;
    }

    private HouseSearchRequest generateHouseSearchRequest() {
        var request = requestGenerator.getHouseSearchRequest();
        String region = requestGenerator.getRegion();
        log.info("Регион поискового запроса дома: {}", region);
        searchClient.createSearchRequest(region, requestGenerator.getHouseParams(request), PAGEABLE, PropertyType.HOUSE);
        return request;
    }

    private LandPlotSearchRequest generateLandPlotSearchRequest() {
        var request = requestGenerator.getLandPlotSearchRequest();
        String region = requestGenerator.getRegion();
        log.info("Регион поискового запроса земельного участка: {}", region);
        searchClient.createSearchRequest(region, requestGenerator.getLandPlotParams(request), PAGEABLE, PropertyType.LAND_PLOT);
        return request;
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

    private void approveListings(int total, String token) {
        ExecutorUtil.execute(total, POOL_SIZE, EXECUTOR, ()-> listingClient.approveListing(approveId.getAndIncrement(), token));
    }

    private void publishListings(@NotNull @Positive Integer total,
                                 String token) {
        ExecutorUtil.execute(total, POOL_SIZE, EXECUTOR, ()-> listingClient.publishListing(publishId.getAndIncrement(), token));
    }
}
