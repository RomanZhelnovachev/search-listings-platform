package ru.romzheln.search_service.resolver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.repository.ListingApartmentReadModelRepository;
import ru.romzheln.search_service.repository.ListingCommercialReadModelRepository;
import ru.romzheln.search_service.repository.ListingHouseReadModelRepository;
import ru.romzheln.search_service.repository.ListingLandPlotReadModelRepository;

@Component
@RequiredArgsConstructor
public class ReadModelResolver {

    private final ListingApartmentReadModelRepository apartmentRepository;
    private final ListingCommercialReadModelRepository commercialRepository;
    private final ListingHouseReadModelRepository houseRepository;
    private final ListingLandPlotReadModelRepository landPlotRepository;




}
