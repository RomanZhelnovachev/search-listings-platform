package ru.romzheln.data_generator.httpClient;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.romzheln.data_generator.dto.request.TokenRequest;
import ru.romzheln.data_generator.dto.response.TokenResponse;

@Component
public class AuthClient {

    private final RestClient restClient;
    private static final String NAME = "Jone";
    private static final String ROLE = "ROLE_ADMIN";

    public AuthClient() {
        this.restClient = RestClient.builder()
                .baseUrl("http://auth-mock:8080")
                .build();
    }

    public String getToken() {
        TokenRequest request = new TokenRequest(NAME, ROLE);
        TokenResponse response = restClient.post()
                .uri("/api/v1/auth/jwt")
                .body(request)
                .retrieve()
                .body(TokenResponse.class);
        return response.token();
    }
}
