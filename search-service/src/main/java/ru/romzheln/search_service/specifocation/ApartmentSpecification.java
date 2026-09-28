package ru.romzheln.search_service.specifocation;

import static ru.romzheln.search_service.specifocation.CommonPhysicalSpecification.getCommonPhysicalSpec;
import static ru.romzheln.search_service.specifocation.CommonSpecification.getCommonSpec;

import java.math.BigDecimal;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.romzheln.search_service.dto.criteria.ApartmentPhysicalCriteria;
import ru.romzheln.search_service.dto.criteria.CommonPhysicalCriteria;
import ru.romzheln.search_service.dto.criteria.CommonSearchCriteria;
import ru.romzheln.search_service.dto.request.ApartmentSearchRequest;
import ru.romzheln.search_service.model.enums.*;
import ru.romzheln.search_service.model.read_model.ListingApartmentReadModel;
import ru.romzheln.search_service.model.read_model.ReadModel;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApartmentSpecification {

  private static final String APARTMENT = "apartment";
  private static final String PHYSICAL = "apartmentPhysicalDetails";

  public static Specification<ListingApartmentReadModel> getApartmentSpecification(ApartmentSearchRequest request) {
      if(request == null){
          return null;
      }
    return Specification.allOf(
        getCommonSpecification(request.commonSearchCriteria()),
        apartmentType(request.apartmentTypes()),
        getCommonPhysicalSpecification(request.commonPhysicalCriteria()),
        getApartmentPhysicalSpecification(request.apartmentPhysicalCriteria()),
        developerIds(request.developerIds()),
        complexIds(request.complexIds()));
  }

  private static Specification<ListingApartmentReadModel> complexIds(Set<Long> complexIds) {
    if (complexIds == null || complexIds.isEmpty()) {
      return null;
    }
    return (root, query, criteriaBuilder) ->
        root.get(APARTMENT).get("complex").get("complexId").in(complexIds);
  }

  private static Specification<ListingApartmentReadModel> developerIds(Set<Long> developerIds) {
    if (developerIds == null || developerIds.isEmpty()) {
      return null;
    }
    return (root, query, criteriaBuilder) ->
        root.get(APARTMENT).get("developer").get("developerId").in(developerIds);
  }

  private static Specification<ListingApartmentReadModel> getApartmentPhysicalSpecification(
      ApartmentPhysicalCriteria criteria) {
    if (criteria == null) {
      return null;
    }
    return Specification.allOf(
        kitchenSquareFrom(criteria.kitchenSquareFrom()),
        kitchenSquareTo(criteria.kitchenSquareTo()),
        floorFrom(criteria.floorFrom()),
        floorTo(criteria.floorTo()),
        inElevators(criteria.elevators()),
        inRamps(criteria.ramps()),
        inSides(criteria.sides()));
  }

  private static Specification<ListingApartmentReadModel> inSides(Set<Side> sides) {
    if (sides == null || sides.isEmpty()) {
      return null;
    }
    return (root, query, criteriaBuilder) ->
        root.get(APARTMENT).get(PHYSICAL).get("side").in(sides);
  }

  private static Specification<ListingApartmentReadModel> inRamps(Set<Ramp> ramps) {
    if (ramps == null || ramps.isEmpty()) {
      return null;
    }
    return (root, query, criteriaBuilder) ->
        root.get(APARTMENT).get(PHYSICAL).get("ramp").in(ramps);
  }

  private static Specification<ListingApartmentReadModel> inElevators(Set<Elevator> elevators) {
    if (elevators == null || elevators.isEmpty()) {
      return null;
    }
    return (root, query, criteriaBuilder) ->
        root.get(APARTMENT).get(PHYSICAL).get("elevator").in(elevators);
  }

  private static Specification<ListingApartmentReadModel> floorTo(Integer floorTo) {
    if (floorTo == null) {
      return null;
    }
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.lessThanOrEqualTo(root.get(APARTMENT).get(PHYSICAL).get("floor"), floorTo);
  }

  private static Specification<ListingApartmentReadModel> floorFrom(Integer floorFrom) {
    if (floorFrom == null) {
      return null;
    }
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.greaterThanOrEqualTo(
            root.get(APARTMENT).get(PHYSICAL).get("floor"), floorFrom);
  }

  private static Specification<ListingApartmentReadModel> kitchenSquareTo(
      BigDecimal kitchenSquareTo) {
    if (kitchenSquareTo == null) {
      return null;
    }
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.lessThanOrEqualTo(
            root.get(APARTMENT).get(PHYSICAL).get("kitchenSquare"), kitchenSquareTo);
  }

  private static Specification<ListingApartmentReadModel> kitchenSquareFrom(
      BigDecimal kitchenSquareFrom) {
    if (kitchenSquareFrom == null) {
      return null;
    }
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.greaterThanOrEqualTo(
            root.get(APARTMENT).get(PHYSICAL).get("kitchenSquare"), kitchenSquareFrom);
  }

  private static <T extends ReadModel> Specification<T> getCommonPhysicalSpecification(
      CommonPhysicalCriteria criteria) {
    if (criteria == null) {
      return null;
    }
    return getCommonPhysicalSpec(PropertyType.APARTMENT, criteria);
  }

  private static Specification<ListingApartmentReadModel> getCommonSpecification(
      CommonSearchCriteria criteria) {
    if (criteria == null) {
      return null;
    }
    return getCommonSpec(criteria);
  }

  private static Specification<ListingApartmentReadModel> apartmentType(Set<ApartmentType> types) {
    if (types == null || types.isEmpty()) {
      return null;
    }
    return (root, query, criteriaBuilder) -> root.get(APARTMENT).get("apartmentType").in(types);
  }
}
