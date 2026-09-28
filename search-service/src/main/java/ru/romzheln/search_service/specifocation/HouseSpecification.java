package ru.romzheln.search_service.specifocation;

import static ru.romzheln.search_service.specifocation.CommonLandSpecification.getCommonLandSpecification;
import static ru.romzheln.search_service.specifocation.CommonPhysicalSpecification.getCommonPhysicalSpec;
import static ru.romzheln.search_service.specifocation.CommonSpecification.getCommonSpec;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import java.math.BigDecimal;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.romzheln.search_service.dto.criteria.CommonLandCriteria;
import ru.romzheln.search_service.dto.criteria.CommonPhysicalCriteria;
import ru.romzheln.search_service.dto.criteria.CommonSearchCriteria;
import ru.romzheln.search_service.dto.request.HouseSearchRequest;
import ru.romzheln.search_service.model.enums.ConstructionStage;
import ru.romzheln.search_service.model.enums.PropertyType;
import ru.romzheln.search_service.model.read_model.ListingHouseReadModel;
import ru.romzheln.search_service.model.read_model.ReadModel;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HouseSpecification {

  private static final String HOUSE = "house";

  public static Specification<ListingHouseReadModel> getHouseSpecification(
      HouseSearchRequest request) {
    if (request == null) {
      return null;
    }
    return Specification.allOf(
        getCommonSpecification(request.commonSearchCriteria()),
        getCommonPhysicalSpecification(request.commonPhysicalCriteria()),
        getCommonLandSpec(request.commonLandCriteria()),
        developerIds(request.developerIds()),
        complexIds(request.complexIds()),
        inConstructionStage(request.constructionStages()),
        inAdditionalBuildings(request.additionalBuildingIds()),
        landPlotSquareFrom(request.landPlotSquareFrom()),
        landPlotSquareTo(request.landPlotSquareTo()));
  }

  private static Specification<ListingHouseReadModel> landPlotSquareTo(BigDecimal to) {
    if (to == null) {
      return null;
    }
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.lessThanOrEqualTo(root.get(HOUSE).get("landPlotSquare"), to);
  }

  private static Specification<ListingHouseReadModel> landPlotSquareFrom(BigDecimal from) {
    if (from == null) {
      return null;
    }
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.greaterThanOrEqualTo(root.get(HOUSE).get("landPlotSquare"), from);
  }

  private static Specification<ListingHouseReadModel> inAdditionalBuildings(Set<Long> ids) {
    if (ids == null || ids.isEmpty()) {
      return null;
    }
    return (root, query, criteriaBuilder) -> {
      Join<?, ?> house = root.join(HOUSE, JoinType.INNER);
      Join<?, Long> additionalBuildingIds = house.join("additionalBuildings", JoinType.INNER);
      query.distinct(true);
      return additionalBuildingIds.in(ids);
    };
  }

  private static Specification<ListingHouseReadModel> inConstructionStage(
      Set<ConstructionStage> stages) {
    if (stages == null || stages.isEmpty()) {
      return null;
    }
    return (root, query, criteriaBuilder) -> root.get(HOUSE).get("constructionStage").in(stages);
  }

  private static Specification<ListingHouseReadModel> getCommonLandSpec(
      CommonLandCriteria criteria) {
    if (criteria == null) {
      return null;
    }
    return getCommonLandSpecification(criteria, PropertyType.HOUSE);
  }

  private static Specification<ListingHouseReadModel> getCommonSpecification(
      CommonSearchCriteria criteria) {
    if (criteria == null) {
      return null;
    }
    return getCommonSpec(criteria);
  }

  private static <T extends ReadModel> Specification<T> getCommonPhysicalSpecification(
      CommonPhysicalCriteria criteria) {
    if (criteria == null) {
      return null;
    }
    return getCommonPhysicalSpec(PropertyType.HOUSE, criteria);
  }

  private static Specification<ListingHouseReadModel> complexIds(Set<Long> complexIds) {
    if (complexIds == null || complexIds.isEmpty()) {
      return null;
    }
    return (root, query, criteriaBuilder) ->
        root.get(HOUSE).get("complex").get("complexId").in(complexIds);
  }

  private static Specification<ListingHouseReadModel> developerIds(Set<Long> developerIds) {
    if (developerIds == null || developerIds.isEmpty()) {
      return null;
    }
    return (root, query, criteriaBuilder) ->
        root.get(HOUSE).get("developer").get("developerId").in(developerIds);
  }
}
