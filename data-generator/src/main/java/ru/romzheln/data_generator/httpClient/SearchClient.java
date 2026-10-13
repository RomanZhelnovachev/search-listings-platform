package ru.romzheln.data_generator.httpClient;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.romzheln.data_generator.enums.PropertyType;

@Component
public class SearchClient {

    private final RestClient restClient;

    private static final String BASE_URL = "http://search-service:8080/api/v1/search";
    private static final String APARTMENT_URL = "/{region}/apartments";
    private static final String COMMERCIAL_URL = "/{region}/commercials";
    private static final String HOUSE_URL = "/{region}/houses";
    private static final String LAND_PLOT_URL = "/{region}/land-plots";

    public SearchClient() {
        this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }

    public void createSearchRequest(String region, Map<String, List<String>> params, Pageable pageable, PropertyType type) {
        String path = getPath(type);
        restClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path(path)
                            .queryParam("page", pageable.getPageNumber())
                            .queryParam("size", pageable.getPageSize());
                    pageable.getSort().forEach(order ->
                            uriBuilder.queryParam("sort",
                                    order.getProperty() + "," + order.getDirection()));
                    params.forEach((name, values) ->
                            uriBuilder.queryParam(name, values.toArray())
                    );
                    return uriBuilder.build(Map.of("region", region));
                })
                .retrieve()
                .toBodilessEntity();
    }

    private String getPath(PropertyType type){
        return switch (type){
            case APARTMENT -> APARTMENT_URL;
            case COMMERCIAL -> COMMERCIAL_URL;
            case HOUSE -> HOUSE_URL;
            case LAND_PLOT -> LAND_PLOT_URL;
        };
    }
}
