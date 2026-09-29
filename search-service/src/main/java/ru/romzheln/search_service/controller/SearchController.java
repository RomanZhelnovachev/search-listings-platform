package ru.romzheln.search_service.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.romzheln.search_service.dto.request.ApartmentSearchRequest;
import ru.romzheln.search_service.dto.request.CommercialSearchRequest;
import ru.romzheln.search_service.dto.request.HouseSearchRequest;
import ru.romzheln.search_service.dto.request.LandPlotSearchRequest;
import ru.romzheln.search_service.dto.response.ApartmentResponse;
import ru.romzheln.search_service.dto.response.CommercialResponse;
import ru.romzheln.search_service.dto.response.HouseResponse;
import ru.romzheln.search_service.dto.response.LandPlotResponse;

@RequestMapping("/api/v1/search")
public interface SearchController {

    @GetMapping("/{region}/apartments")
    Page<ApartmentResponse> getApartments(@PathVariable String region, @Valid
                                           ApartmentSearchRequest request, Pageable pageable);

    @GetMapping("/{region}/commercials")
    Page<CommercialResponse> getCommercials(@PathVariable String region, @Valid
    CommercialSearchRequest request, Pageable pageable);

    @GetMapping("/{region}/houses")
    Page<HouseResponse> getHouses(@PathVariable String region, @Valid
    HouseSearchRequest request, Pageable pageable);

    @GetMapping("/{region}/land-plots")
    Page<LandPlotResponse> getLandPlots(@PathVariable String region, @Valid
    LandPlotSearchRequest request, Pageable pageable);
}
