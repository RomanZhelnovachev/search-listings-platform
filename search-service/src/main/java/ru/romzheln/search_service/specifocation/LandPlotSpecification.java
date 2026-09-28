package ru.romzheln.search_service.specifocation;

import static ru.romzheln.search_service.specifocation.CommonLandSpecification.getCommonLandSpecification;
import static ru.romzheln.search_service.specifocation.CommonSpecification.getCommonSpec;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.romzheln.search_service.dto.criteria.CommonLandCriteria;
import ru.romzheln.search_service.dto.criteria.CommonSearchCriteria;
import ru.romzheln.search_service.dto.request.LandPlotSearchRequest;
import ru.romzheln.search_service.model.enums.PropertyType;
import ru.romzheln.search_service.model.read_model.ListingLandPlotReadModel;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LandPlotSpecification {
    
    public static Specification<ListingLandPlotReadModel> getLandPlotSpecification(LandPlotSearchRequest request){
        if(request == null){
            return null;
        }
        return Specification.allOf(
                getCommonSpecification(request.commonSearchCriteria()),
                getCommonLandSpec(request.commonLandCriteria()),
                inAdditionalBuildings(request.additionalBuildingIds())
        );
    }

    private static Specification<ListingLandPlotReadModel> getCommonSpecification(CommonSearchCriteria criteria) {
        if (criteria == null) {
            return null;
        }
        return getCommonSpec(criteria);
    }

    private static Specification<ListingLandPlotReadModel> getCommonLandSpec(CommonLandCriteria criteria) {
        if (criteria == null) {
            return null;
        }
        return getCommonLandSpecification(criteria, PropertyType.LAND_PLOT);
    }

    private static Specification<ListingLandPlotReadModel> inAdditionalBuildings(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return null;
        }
        return (root, query, criteriaBuilder) -> {
            Join<?, ?> landPlots = root.join("landPlot", JoinType.INNER);
            Join<?, Long> additionalBuildingIds = landPlots.join("additionalBuildings", JoinType.INNER);
            query.distinct(true);
            return additionalBuildingIds.in(ids);
        };
    }
}
