package ru.romzheln.search_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romzheln.search_service.dto.request.ApartmentSearchRequest;
import ru.romzheln.search_service.dto.request.CommercialSearchRequest;
import ru.romzheln.search_service.dto.request.HouseSearchRequest;
import ru.romzheln.search_service.dto.request.LandPlotSearchRequest;
import ru.romzheln.search_service.dto.response.ApartmentResponse;
import ru.romzheln.search_service.dto.response.CommercialResponse;
import ru.romzheln.search_service.dto.response.HouseResponse;
import ru.romzheln.search_service.dto.response.LandPlotResponse;
import ru.romzheln.search_service.mapper.responseMapper.SearchResponseMapper;
import ru.romzheln.search_service.model.enums.Region;
import ru.romzheln.search_service.model.read_model.*;
import ru.romzheln.search_service.repository.ListingApartmentReadModelRepository;
import ru.romzheln.search_service.repository.ListingCommercialReadModelRepository;
import ru.romzheln.search_service.repository.ListingHouseReadModelRepository;
import ru.romzheln.search_service.repository.ListingLandPlotReadModelRepository;
import ru.romzheln.search_service.service.SearchService;

import java.util.stream.Collectors;

import static ru.romzheln.search_service.specifocation.ApartmentSpecification.getApartmentSpecification;
import static ru.romzheln.search_service.specifocation.CommercialSpecification.getCommercialSpecification;
import static ru.romzheln.search_service.specifocation.HouseSpecification.getHouseSpecification;
import static ru.romzheln.search_service.specifocation.LandPlotSpecification.getLandPlotSpecification;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchServiceImpl implements SearchService {

    private final ListingApartmentReadModelRepository apartmentRepository;
    private final ListingCommercialReadModelRepository commercialRepository;
    private final ListingHouseReadModelRepository houseRepository;
    private final ListingLandPlotReadModelRepository landPlotRepository;
    private final SearchResponseMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ApartmentResponse> findApartments(Region region,
                                                  ApartmentSearchRequest request,
                                                  Pageable pageable) {
        Specification<ListingApartmentReadModel> specification = getApartmentSpecification(request);
        specification = addRegion(specification, region);
        specification = onSearch(specification);
        Page<ApartmentResponse> page = apartmentRepository.findAll(specification,
                        pageable)
                .map(mapper::toApartmentResponse);
        log.info("по запросу нашлось {} вариантов квартир", page.getTotalElements());
        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommercialResponse> findCommercials(Region region,
                                                    CommercialSearchRequest request,
                                                    Pageable pageable) {
        Specification<ListingCommercialReadModel> specification = getCommercialSpecification(request);
        specification = addRegion(specification, region);
        specification = onSearch(specification);
        Page<CommercialResponse> page = commercialRepository.findAll(specification,
                        pageable)
                .map(mapper::toCommercialResponse);
        log.info("по запросу нашлось {} вариантов коммерческой недвижимости", page.getTotalElements());
        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HouseResponse> findHouses(Region region,
                                          HouseSearchRequest request,
                                          Pageable pageable) {
        Specification<ListingHouseReadModel> specification = getHouseSpecification(request);
        specification = addRegion(specification, region);
        specification = onSearch(specification);
        Page<HouseResponse> page = houseRepository.findAll(specification,
                        pageable)
                .map(mapper::toHouseResponse);
        log.info("по запросу нашлось {} вариантов домов", page.getTotalElements());
        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LandPlotResponse> findLandPlots(Region region,
                                                LandPlotSearchRequest request,
                                                Pageable pageable) {
        Specification<ListingLandPlotReadModel> specification = getLandPlotSpecification(request);
        specification = addRegion(specification, region);
        specification = onSearch(specification);
        Page<LandPlotResponse> page = landPlotRepository.findAll(specification,
                        pageable)
                .map(mapper::toLandPlotResponse);
        log.info("по запросу нашлось {} вариантов земельных участков", page.getTotalElements());
        return page;
    }

    private <T extends ReadModel> Specification<T> addRegion(Specification<T> specification, Region region){
        return specification.and((root,query,criteriaBuilder) ->
                criteriaBuilder.equal(root.get("key").get("region"), region));
    }

    private <T extends ReadModel> Specification<T> onSearch(Specification<T> specification) {
        return specification.and((root,query,criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isIncludedInSearch"), true));
    }
}
