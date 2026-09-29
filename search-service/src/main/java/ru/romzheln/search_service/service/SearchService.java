package ru.romzheln.search_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.romzheln.search_service.dto.request.ApartmentSearchRequest;
import ru.romzheln.search_service.dto.request.CommercialSearchRequest;
import ru.romzheln.search_service.dto.request.HouseSearchRequest;
import ru.romzheln.search_service.dto.request.LandPlotSearchRequest;
import ru.romzheln.search_service.dto.response.ApartmentResponse;
import ru.romzheln.search_service.dto.response.CommercialResponse;
import ru.romzheln.search_service.dto.response.HouseResponse;
import ru.romzheln.search_service.dto.response.LandPlotResponse;
import ru.romzheln.search_service.model.enums.Region;

public interface SearchService {

  Page<ApartmentResponse> findApartments(
      Region region, ApartmentSearchRequest request, Pageable pageable);

  Page<CommercialResponse> findCommercials(
      Region region, CommercialSearchRequest request, Pageable pageable);

  Page<HouseResponse> findHouses(Region region, HouseSearchRequest request, Pageable pageable);

  Page<LandPlotResponse> findLandPlots(
      Region region, LandPlotSearchRequest request, Pageable pageable);
}
