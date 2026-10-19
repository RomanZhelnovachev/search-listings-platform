package ru.romzheln.data_generator.mapper;

import static ru.romzheln.data_generator.util.MapperUtil.putOnceToMap;
import static ru.romzheln.data_generator.util.MapperUtil.putSetToMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import ru.romzheln.data_generator.dto.criteria.*;
import ru.romzheln.data_generator.dto.request.ApartmentSearchRequest;
import ru.romzheln.data_generator.dto.request.CommercialSearchRequest;
import ru.romzheln.data_generator.dto.request.HouseSearchRequest;
import ru.romzheln.data_generator.dto.request.LandPlotSearchRequest;

@Component
public class SearchStringMapper {

    private static final String COMMON_SEARCH = "commonSearchCriteria.";
    private static final String COMMON_PHYSICAL = "commonPhysicalCriteria.";
    private static final String APARTMENT_PHYSICAL = "apartmentPhysicalCriteria.";
    private static final String COMMERCIAL_PHYSICAL = "commercialPhysicalCriteria.";
    private static final String COMMON_LAND = "commonLandCriteria.";

    public Map<String, List<String>> mapFromApartmentSearchRequest(ApartmentSearchRequest request){
        Map<String, List<String>> params = new HashMap<>();
        mapFromCommonSearchCriteria(params, request.commonSearchCriteria());
        putSetToMap(params, "apartmentTypes", request.apartmentTypes());
        mapFromCommonPhysicalCriteria(params, request.commonPhysicalCriteria());
        mapFromApartmentPhysicalCriteria(params, request.apartmentPhysicalCriteria());
        putSetToMap(params, "developerIds", request.developerIds());
        putSetToMap(params, "complexIds", request.complexIds());
        return params;
    }

    public Map<String, List<String>> mapFromCommercialSearchRequest(CommercialSearchRequest request){
        Map<String, List<String>> params = new HashMap<>();
        mapFromCommonSearchCriteria(params, request.commonSearchCriteria());
        mapFromCommonPhysicalCriteria(params, request.commonPhysicalCriteria());
        mapFromCommercialPhysicalCriteria(params, request.commercialPhysicalCriteria());
        putSetToMap(params, "purposeIds", request.purposeIds());
        return params;
    }

    public Map<String, List<String>> mapFromHouseSearchRequest(HouseSearchRequest request){
        Map<String, List<String>> params = new HashMap<>();
        mapFromCommonSearchCriteria(params, request.commonSearchCriteria());
        mapFromCommonPhysicalCriteria(params, request.commonPhysicalCriteria());
        mapFromCommonLandCriteria(params, request.commonLandCriteria());
        putSetToMap(params, "developerIds", request.developerIds());
        putSetToMap(params, "complexIds", request.complexIds());
        putSetToMap(params, "constructionStages", request.constructionStages());
        putSetToMap(params, "additionalBuildingIds", request.additionalBuildingIds());
        putOnceToMap(params, "landPlotSquareFrom", request.landPlotSquareFrom());
        putOnceToMap(params, "landPlotSquareTo", request.landPlotSquareTo());
        return params;
    }

    public Map<String, List<String>> mapFromLandPlotSearchRequest(LandPlotSearchRequest request){
        Map<String, List<String>> params = new HashMap<>();
        mapFromCommonSearchCriteria(params, request.commonSearchCriteria());
        mapFromCommonLandCriteria(params, request.commonLandCriteria());
        putSetToMap(params, "additionalBuildingIds", request.additionalBuildingIds());
        return params;
    }

    private void mapFromCommonSearchCriteria(Map<String, List<String>> params, CommonSearchCriteria criteria){
        if(criteria == null){
            return;
        }
        putOnceToMap(params, COMMON_SEARCH + "hasImage", criteria.hasImage());
        putSetToMap(params, COMMON_SEARCH + "owns", criteria.owns());
        putOnceToMap(params, COMMON_SEARCH + "squareFrom", criteria.squareFrom());
        putOnceToMap(params, COMMON_SEARCH + "squareTo", criteria.squareTo());
        putOnceToMap(params, COMMON_SEARCH + "firstOwner", criteria.firstOwner());
        putSetToMap(params,COMMON_SEARCH + "dealTypes", criteria.dealTypes());
        putOnceToMap(params, COMMON_SEARCH + "priceFrom", criteria.priceFrom());
        putOnceToMap(params, COMMON_SEARCH + "priceTo", criteria.priceTo());
        putSetToMap(params,COMMON_SEARCH + "mortgageProgramIds", criteria.mortgageProgramIds());
        putOnceToMap(params, COMMON_SEARCH + "promotionId", criteria.promotionId());
    }

    private void mapFromCommonPhysicalCriteria(Map<String, List<String>> params, CommonPhysicalCriteria criteria){
        if(criteria == null){
            return;
        }
        putSetToMap(params, COMMON_PHYSICAL + "roomsNumber", criteria.roomsNumber());
        putOnceToMap(params, COMMON_PHYSICAL + "ceilingHeightFrom", criteria.ceilingHeightFrom());
        putOnceToMap(params, COMMON_PHYSICAL + "ceilingHeightTo", criteria.ceilingHeightTo());
        putSetToMap(params, COMMON_PHYSICAL + "renovations", criteria.renovations());
        putSetToMap(params, COMMON_PHYSICAL + "bathrooms", criteria.bathrooms());
        putSetToMap(params, COMMON_PHYSICAL + "wallMaterials", criteria.wallMaterials());
        putOnceToMap(params, COMMON_PHYSICAL + "completionDate", criteria.completionDate());
        putOnceToMap(params, COMMON_PHYSICAL + "yearBuiltFrom", criteria.yearBuiltFrom());
        putOnceToMap(params, COMMON_PHYSICAL + "yearBuiltTo", criteria.yearBuiltTo());
        putOnceToMap(params, COMMON_PHYSICAL + "floorsNumberFrom", criteria.floorsNumberFrom());
        putOnceToMap(params, COMMON_PHYSICAL + "floorsNumberTo", criteria.floorsNumberTo());
        putSetToMap(params, COMMON_PHYSICAL + "views", criteria.views());
        putSetToMap(params, COMMON_PHYSICAL + "balconies", criteria.balconies());
        putSetToMap(params, COMMON_PHYSICAL + "windowTypes", criteria.windowTypes());
        putSetToMap(params, COMMON_PHYSICAL + "windowMaterials", criteria.windowMaterials());
        putSetToMap(params, COMMON_PHYSICAL + "layoutFeatures", criteria.layoutFeatures());
        putSetToMap(params, COMMON_PHYSICAL + "layoutTypes", criteria.layoutTypes());
    }

    private void mapFromApartmentPhysicalCriteria(Map<String, List<String>> params, ApartmentPhysicalCriteria criteria){
        if (criteria == null){
            return;
        }
        putOnceToMap(params, APARTMENT_PHYSICAL + "kitchenSquareFrom", criteria.kitchenSquareFrom());
        putOnceToMap(params, APARTMENT_PHYSICAL + "kitchenSquareTo", criteria.kitchenSquareTo());
        putOnceToMap(params, APARTMENT_PHYSICAL + "floorFrom", criteria.floorFrom());
        putOnceToMap(params, APARTMENT_PHYSICAL + "floorTo", criteria.floorTo());
        putSetToMap(params, APARTMENT_PHYSICAL + "elevators", criteria.elevators());
        putSetToMap(params, APARTMENT_PHYSICAL + "ramps", criteria.ramps());
        putSetToMap(params, APARTMENT_PHYSICAL + "sides", criteria.sides());
    }

    private void mapFromCommercialPhysicalCriteria(Map<String, List<String>> params, CommercialPhysicalCriteria criteria){
        if (criteria == null){
            return;
        }
        putOnceToMap(params, COMMERCIAL_PHYSICAL + "floorFrom", criteria.floorFrom());
        putOnceToMap(params, COMMERCIAL_PHYSICAL + "floorTo", criteria.floorTo());
        putSetToMap(params, COMMERCIAL_PHYSICAL + "lines", criteria.lines());
        putSetToMap(params, COMMERCIAL_PHYSICAL + "propertyLocationTypes", criteria.propertyLocationTypes());
        putSetToMap(params, COMMERCIAL_PHYSICAL + "territorialZones", criteria.territorialZones());
        putOnceToMap(params, COMMERCIAL_PHYSICAL + "separateEntrance", criteria.separateEntrance());
        putOnceToMap(params, COMMERCIAL_PHYSICAL + "ventilation", criteria.ventilation());
        putOnceToMap(params, COMMERCIAL_PHYSICAL + "tenantExists", criteria.tenantExists());
        putOnceToMap(params, COMMERCIAL_PHYSICAL + "entrancesNumberFrom", criteria.entrancesNumberFrom());
        putOnceToMap(params, COMMERCIAL_PHYSICAL + "entrancesNumberTo", criteria.entrancesNumberTo());
        putOnceToMap(params, COMMERCIAL_PHYSICAL + "electricalPowerKwFrom", criteria.electricalPowerKwFrom());
        putOnceToMap(params, COMMERCIAL_PHYSICAL + "electricalPowerKwTo", criteria.electricalPowerKwTo());
        putOnceToMap(params, COMMERCIAL_PHYSICAL + "railwayDeadEnd", criteria.railwayDeadEnd());
    }

    private void mapFromCommonLandCriteria(Map<String, List<String>> params, CommonLandCriteria criteria){
        if (criteria == null){
            return;
        }
        putSetToMap(params, COMMON_LAND + "landUseIds", criteria.landUseIds());
    }
}

