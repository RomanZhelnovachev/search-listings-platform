package ru.romzheln.search_service.specifocation;

import static ru.romzheln.search_service.specifocation.CommonPhysicalSpecification.getCommonPhysicalSpec;
import static ru.romzheln.search_service.specifocation.CommonSpecification.getCommonSpec;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.romzheln.search_service.dto.criteria.CommercialPhysicalCriteria;
import ru.romzheln.search_service.dto.criteria.CommonPhysicalCriteria;
import ru.romzheln.search_service.dto.criteria.CommonSearchCriteria;
import ru.romzheln.search_service.dto.request.CommercialSearchRequest;
import ru.romzheln.search_service.model.enums.Line;
import ru.romzheln.search_service.model.enums.PropertyLocationType;
import ru.romzheln.search_service.model.enums.PropertyType;
import ru.romzheln.search_service.model.enums.TerritorialZone;
import ru.romzheln.search_service.model.read_model.ListingCommercialReadModel;
import ru.romzheln.search_service.model.read_model.ReadModel;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommercialSpecification {

  private static final String COMMERCIAL = "commercial";
  private static final String PHYSICAL = "commercialPhysicalDetails";

  public static Specification<ListingCommercialReadModel> getCommercialSpecification(
      CommercialSearchRequest request) {
    if (request == null) {
      return null;
    }
    return Specification.allOf(
        getCommonSpecification(request.commonSearchCriteria()),
        getCommonPhysicalSpecification(request.commonPhysicalCriteria()),
        getCommercialPhysicalSpecification(request.commercialPhysicalCriteria()),
        purposeIds(request.purposeIds()));
  }

  private static Specification<ListingCommercialReadModel> purposeIds(Set<Long> purposeIds) {
    if (purposeIds == null || purposeIds.isEmpty()) {
      return null;
    }
    return (root, query, criteriaBuilder) -> {
      Join<?, ?> commercial = root.join(COMMERCIAL, JoinType.INNER);
      Join<?, Long> purposes = commercial.join("purposeIds", JoinType.INNER);
      query.distinct(true);
      return purposes.in(purposeIds);
    };
  }

  private static Specification<ListingCommercialReadModel> getCommercialPhysicalSpecification(
      CommercialPhysicalCriteria criteria) {
    if (criteria == null) {
      return null;
    }
    return Specification.allOf(
        intFrom(criteria.floorFrom(), "floor"),
        intTo(criteria.floorTo(), "floor"),
        inLines(criteria.lines()),
        inPropertyLocationTypes(criteria.propertyLocationTypes()),
        inTerritorialZones(criteria.territorialZones()),
        equalsBoolean(criteria.separateEntrance(), "separateEntrance"),
        equalsBoolean(criteria.ventilation(), "ventilation"),
        equalsBoolean(criteria.tenantExists(), "tenantExists"),
        intFrom(criteria.entrancesNumberFrom(), "entrancesNumber"),
        intTo(criteria.entrancesNumberTo(), "entrancesNumber"),
        intFrom(criteria.electricalPowerKwFrom(), "electricalPowerKw"),
        intTo(criteria.electricalPowerKwTo(), "electricalPowerKw"),
        equalsBoolean(criteria.railwayDeadEnd(), "railwayDeadEnd"));
  }

  private static Specification<ListingCommercialReadModel> equalsBoolean(
      Boolean value, String field) {
    if (value == null) {
      return null;
    }
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.equal(root.get(COMMERCIAL).get(PHYSICAL).get(field), value);
  }

  private static Specification<ListingCommercialReadModel> intFrom(Integer value, String field) {
    if (value == null) {
      return null;
    }
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.greaterThanOrEqualTo(root.get(COMMERCIAL).get(PHYSICAL).get(field), value);
  }

  private static Specification<ListingCommercialReadModel> intTo(Integer value, String field) {
    if (value == null) {
      return null;
    }
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.lessThanOrEqualTo(root.get(COMMERCIAL).get(PHYSICAL).get(field), value);
  }

  private static Specification<ListingCommercialReadModel> inTerritorialZones(
      Set<TerritorialZone> zones) {
    if (zones == null || zones.isEmpty()) {
      return null;
    }
    return (root, query, criteriaBuilder) ->
        root.get(COMMERCIAL).get(PHYSICAL).get("territorialZone").in(zones);
  }

  private static Specification<ListingCommercialReadModel> inPropertyLocationTypes(
      Set<PropertyLocationType> types) {
    if (types == null || types.isEmpty()) {
      return null;
    }
    return (root, query, criteriaBuilder) ->
        root.get(COMMERCIAL).get(PHYSICAL).get("propertyLocationType").in(types);
  }

  private static Specification<ListingCommercialReadModel> inLines(Set<Line> lines) {
    if (lines == null || lines.isEmpty()) {
      return null;
    }
    return (root, query, criteriaBuilder) ->
        root.get(COMMERCIAL).get(PHYSICAL).get("line").in(lines);
  }

  private static Specification<ListingCommercialReadModel> getCommonSpecification(
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
    return getCommonPhysicalSpec(PropertyType.COMMERCIAL, criteria);
  }
}
