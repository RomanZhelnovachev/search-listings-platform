package ru.romzheln.search_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.romzheln.search_service.dto.request.ApartmentSearchRequest;
import ru.romzheln.search_service.dto.request.CommercialSearchRequest;
import ru.romzheln.search_service.dto.request.HouseSearchRequest;
import ru.romzheln.search_service.dto.request.LandPlotSearchRequest;
import ru.romzheln.search_service.dto.response.ApartmentResponse;
import ru.romzheln.search_service.dto.response.CommercialResponse;
import ru.romzheln.search_service.dto.response.HouseResponse;
import ru.romzheln.search_service.dto.response.LandPlotResponse;
import ru.romzheln.search_service.model.enums.Region;
import ru.romzheln.search_service.service.SearchService;

@RestController
@RequiredArgsConstructor
public class SearchControllerImpl implements SearchController{
    
    private final SearchService service;
    
    @Override
    public Page<ApartmentResponse> getApartments(String region,
                                                 ApartmentSearchRequest request,
                                                 Pageable pageable) {
        return service.findApartments(Region.getRegionByUrl(region), request, pageable);
    }

    @Override
    public Page<CommercialResponse> getCommercials(String region,
                                                   CommercialSearchRequest request,
                                                   Pageable pageable) {
        return service.findCommercials(Region.getRegionByUrl(region), request, pageable);
    }

    @Override
    public Page<HouseResponse> getHouses(String region,
                                         HouseSearchRequest request,
                                         Pageable pageable) {
        return service.findHouses(Region.getRegionByUrl(region), request, pageable);
    }

    @Override
    public Page<LandPlotResponse> getLandPlots(String region,
                                               LandPlotSearchRequest request,
                                               Pageable pageable) {
        return service.findLandPlots(Region.getRegionByUrl(region), request, pageable);
    }
}
