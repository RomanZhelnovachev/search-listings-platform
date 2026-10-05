package ru.romzheln.data_generator.httpClient;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.romzheln.data_generator.dto.response.*;

@Component
public class ListingClient {

    private final RestClient restClient;

    private static final String BASE_URL = "http://listing-service:8080";
    private static final String BEARER = "Bearer ";
    private static final String DEVELOPER_URI = "/api/v1/developers";
    private static final String COMPLEX_URI = "/api/v1/properties/complexes";
    private static final String ADDITIONAL_BUILDING_URI = "/api/v1/properties/buildings";
    private static final String COMMUNICATION_URI = "/api/v1/properties/communication";
    private static final String LAND_USE_URI = "/api/v1/land_uses";
    private static final String PURPOSE_URI = "/api/v1/properties/purposes";
    private static final String PROPERTY_URI = "/api/v1/properties";
    private static final String LISTING_URI = "/api/v1/listings";

    public ListingClient() {
        this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }

    public void createDeveloper(DeveloperResponse response, String token) {
        restClient.post()
                .uri(DEVELOPER_URI)
                .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                .body(response)
                .retrieve()
                .toBodilessEntity();
    }

    public void createResidentialComplex(ResidentialComplexResponse response, String token){
        restClient.post()
                .uri(COMPLEX_URI)
                .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                .body(response)
                .retrieve()
                .toBodilessEntity();
    }

    public void createAdditionalBuilding(AdditionalBuildingResponse response, String token){
        restClient.post()
                .uri(ADDITIONAL_BUILDING_URI)
                .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                .body(response)
                .retrieve()
                .toBodilessEntity();
    }

    public void createCommunication(CommunicationResponse response, String token){
        restClient.post()
                .uri(COMMUNICATION_URI)
                .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                .body(response)
                .retrieve()
                .toBodilessEntity();
    }

    public void createLandUse(LandUseResponse response, String token){
        restClient.post()
                .uri(LAND_USE_URI)
                .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                .body(response)
                .retrieve()
                .toBodilessEntity();
    }

    public void createPurpose(PurposeResponse response, String token){
        restClient.post()
                .uri(PURPOSE_URI)
                .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                .body(response)
                .retrieve()
                .toBodilessEntity();
    }

    public void createProperty(CreatePropertyResponse response, String token){
        restClient.post()
                .uri(PROPERTY_URI)
                .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                .body(response)
                .retrieve()
                .toBodilessEntity();
    }

    public void createListing(CreateListingResponse response, String token){
        restClient.post()
                .uri(LISTING_URI)
                .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                .body(response)
                .retrieve()
                .toBodilessEntity();
    }
}
