package ru.romzheln.search_service.specifocation;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import java.math.BigDecimal;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.romzheln.search_service.dto.criteria.CommonSearchCriteria;
import ru.romzheln.search_service.model.enums.DealType;
import ru.romzheln.search_service.model.enums.Own;
import ru.romzheln.search_service.model.read_model.ReadModel;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonSpecification {

  private static final String LISTING = "listing";
  private static final String PROPERTY = "property";

  public static <T extends ReadModel> Specification<T> getCommonSpec(
      CommonSearchCriteria criteria) {
    return Specification.allOf(
        hasImage(criteria.hasImage()),
        inOwn(criteria.owns()),
        greaterThanSquare(criteria.squareFrom()),
        lessThanSquare(criteria.squareTo()),
        firstOwner(criteria.firstOwner()),
        inDealType(criteria.dealTypes()),
        greaterThanPrice(criteria.priceFrom()),
        lessThanPrice(criteria.priceTo()),
            inMortgagePrograms(criteria.mortgageProgramIds()));
  }

    private static <T extends ReadModel> Specification<T> inMortgagePrograms(Set<Long> mortgageProgramsIds) {
      if(mortgageProgramsIds == null || mortgageProgramsIds.isEmpty()){
          return null;
      }
    }

    private static <T extends ReadModel> Specification<T> lessThanPrice(BigDecimal priceTo) {
    if (priceTo == null) {
      return null;
    }
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.greaterThanOrEqualTo(root.get(LISTING).get("price"), priceTo);
  }

  private static <T extends ReadModel> Specification<T> greaterThanPrice(BigDecimal priceFrom) {
    if (priceFrom == null) {
      return null;
    }
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.greaterThanOrEqualTo(root.get(LISTING).get("price"), priceFrom);
  }

  private static <T extends ReadModel> Specification<T> inDealType(Set<DealType> dealTypes) {
    if (dealTypes == null || dealTypes.isEmpty()) {
      return null;
    }
    return (root, query, criteriaBuilder) -> root.get(LISTING).get("dealType").in(dealTypes);
  }

  private static <T extends ReadModel> Specification<T> firstOwner(Boolean firstOwner) {
    if (firstOwner == null) {
      return null;
    }
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.equal(root.get(LISTING).get(PROPERTY).get("firstOwner"), firstOwner);
  }

  private static <T extends ReadModel> Specification<T> lessThanSquare(BigDecimal squareTo) {
    if (squareTo == null) {
      return null;
    }
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.lessThanOrEqualTo(root.get(LISTING).get(PROPERTY).get("square"), squareTo);
  }

  private static <T extends ReadModel> Specification<T> greaterThanSquare(BigDecimal squareFrom) {
    if (squareFrom == null) {
      return null;
    }
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.greaterThanOrEqualTo(
            root.get(LISTING).get(PROPERTY).get("square"), squareFrom);
  }

  private static <T extends ReadModel> Specification<T> inOwn(Set<Own> owns) {
    if (owns == null || owns.isEmpty()) {
      return null;
    }
    return (root, query, criteriaBuilder) -> root.get(LISTING).get(PROPERTY).in(owns);
  }

  private static <T extends ReadModel> Specification<T> hasImage(Boolean hasImage) {
    if (hasImage == null) {
      return null;
    }
    return (root, query, criteriaBuilder) -> {
      Join<T, Long> images = root.join("listing.imageIds", JoinType.LEFT);
      if (hasImage) {
        return criteriaBuilder.isNotNull(images);
      }
      return criteriaBuilder.isNull(images);
    };
  }
}
