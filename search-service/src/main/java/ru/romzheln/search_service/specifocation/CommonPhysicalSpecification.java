package ru.romzheln.search_service.specifocation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.romzheln.search_service.dto.criteria.CommonPhysicalCriteria;
import ru.romzheln.search_service.exception.UnsupportedFieldException;
import ru.romzheln.search_service.model.enums.*;
import ru.romzheln.search_service.model.read_model.ReadModel;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonPhysicalSpecification {

    private static final String PHYSICAL = "commonPhysicalDetails";

    public static <T extends ReadModel> Specification<T> getCommonPhysicalSpec(PropertyType type, CommonPhysicalCriteria criteria) {
        if (criteria == null) {
            return null;
        }
        String typeStr = null;
        switch (type){
            case APARTMENT -> typeStr = "apartment";
            case HOUSE -> typeStr = "house";
            case COMMERCIAL -> typeStr = "commercial";
            case LAND_PLOT -> throw new UnsupportedFieldException("У LandPlot нет поля CommonPhysicalDetails");
        }
        return Specification.allOf(
                inRoomsNumber(typeStr, criteria.roomsNumber()),
                ceilingHeightFrom(typeStr, criteria.ceilingHeightFrom()),
                ceilingHeightTo(typeStr, criteria.ceilingHeightTo()),
                inRenovations(typeStr, criteria.renovations()),
                inBathrooms(typeStr, criteria.bathrooms()),
                inWallMaterials(typeStr, criteria.wallMaterials()),
                beforeCompletionDate(typeStr,criteria.completionDate()),
                yearBuiltFrom(typeStr, criteria.yearBuiltFrom()),
                yearBuiltTo(typeStr, criteria.yearBuiltTo()),
                floorsNumberFrom(typeStr, criteria.floorsNumberFrom()),
                floorsNumberTo(typeStr, criteria.floorsNumberTo()),
                inViews(typeStr, criteria.views()),
                inBalconies(typeStr, criteria.balconies()),
                inWindowTypes(typeStr, criteria.windowTypes()),
                inWindowMaterials(typeStr, criteria.windowMaterials()),
                inLayoutFeatures(typeStr, criteria.layoutFeatures()),
                inLayoutTypes(typeStr, criteria.layoutTypes())
        );
    }

    private static <T extends ReadModel> Specification<T> inLayoutTypes(String typeStr, Set<LayoutType> types) {
        if(types == null || types.isEmpty()){
            return null;
        }
        return (root,query,criteriaBuilder) ->
                root.get(typeStr).get(PHYSICAL).get("layoutType").in(types);
    }

    private static <T extends ReadModel> Specification<T> inLayoutFeatures(String typeStr, Set<LayoutFeature> features) {
        if(features == null || features.isEmpty()){
            return null;
        }
        return (root,query,criteriaBuilder) ->
                root.get(typeStr).get(PHYSICAL).get("layoutFeature").in(features);
    }

    private static <T extends ReadModel> Specification<T> inWindowMaterials(String typeStr, Set<WindowMaterial> materials) {
        if(materials == null || materials.isEmpty()){
            return null;
        }
        return (root,query,criteriaBuilder) ->
                root.get(typeStr).get(PHYSICAL).get("windowMaterial").in(materials);
    }

    private static <T extends ReadModel> Specification<T> inWindowTypes(String typeStr, Set<WindowType> types) {
        if(types == null || types.isEmpty()){
            return null;
        }
        return (root,query,criteriaBuilder) ->
                root.get(typeStr).get(PHYSICAL).get("windowType").in(types);
    }

    private static <T extends ReadModel> Specification<T> inBalconies(String typeStr, Set<Balcony> balconies) {
        if(balconies == null || balconies.isEmpty()){
            return null;
        }
        return (root,query,criteriaBuilder) ->
                root.get(typeStr).get(PHYSICAL).get("balcony").in(balconies);
    }

    private static <T extends ReadModel> Specification<T> inViews(String typeStr, Set<WindowView> views) {
        if(views == null || views.isEmpty()){
            return null;
        }
        return (root,query,criteriaBuilder) ->
                root.get(typeStr).get(PHYSICAL).get("view").in(views);
    }

    private static <T extends ReadModel> Specification<T> floorsNumberTo(String typeStr, Integer floorsNumberTo) {
        if(floorsNumberTo == null){
            return null;
        }
        return (root,query,criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(typeStr).get(PHYSICAL).get("floorsNumber"), floorsNumberTo);
    }

    private static <T extends ReadModel> Specification<T> floorsNumberFrom(String typeStr, Integer floorsNumberFrom) {
        if(floorsNumberFrom == null){
            return null;
        }
        return (root,query,criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get(typeStr).get(PHYSICAL).get("floorsNumber"), floorsNumberFrom);
    }

    private static <T extends ReadModel> Specification<T> yearBuiltTo(String typeStr, Integer yearBuiltTo) {
        if(yearBuiltTo == null){
            return null;
        }
        return (root,query,criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(typeStr).get(PHYSICAL).get("yearBuilt"), yearBuiltTo);
    }

    private static <T extends ReadModel> Specification<T> yearBuiltFrom(String typeStr, Integer yearBuiltFrom) {
        if(yearBuiltFrom == null){
            return null;
        }
        return (root,query,criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get(typeStr).get(PHYSICAL).get("yearBuilt"), yearBuiltFrom);
    }

    private static <T extends ReadModel> Specification<T> beforeCompletionDate(String typeStr, LocalDate completionDate) {
        if(completionDate == null){
            return null;
        }
        return (root,query,criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(typeStr).get(PHYSICAL).get("completionDate"), completionDate);
    }

    private static <T extends ReadModel> Specification<T> inWallMaterials(String typeStr, Set<WallMaterial> wallMaterials) {
        if(wallMaterials == null || wallMaterials.isEmpty()){
            return null;
        }
        return (root,query,criteriaBuilder) ->
                root.get(typeStr).get(PHYSICAL).get("material").in(wallMaterials);
    }

    private static <T extends ReadModel> Specification<T> inBathrooms(String typeStr, Set<Bathroom> bathrooms) {
        if(bathrooms == null || bathrooms.isEmpty()){
            return null;
        }
        return (root,query,criteriaBuilder) ->
                root.get(typeStr).get(PHYSICAL).get("bathroom").in(bathrooms);
    }

    private static <T extends ReadModel> Specification<T> inRenovations(String typeStr, Set<Renovation> renovations) {
        if(renovations == null || renovations.isEmpty()){
            return null;
        }
        return (root,query,criteriaBuilder) ->
                root.get(typeStr).get(PHYSICAL).get("renovation").in(renovations);
    }

    private static <T extends ReadModel> Specification<T> ceilingHeightTo(String typeStr, BigDecimal ceilingHeightTo) {
        if(ceilingHeightTo == null){
            return null;
        }
        return (root,query,criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(typeStr).get(PHYSICAL).get("ceilingHeight"), ceilingHeightTo);
    }

    private static <T extends ReadModel> Specification<T> ceilingHeightFrom(String typeStr, BigDecimal ceilingHeightFrom) {
        if (ceilingHeightFrom == null) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(
                        root.get(typeStr).get(PHYSICAL).get("ceilingHeight"), ceilingHeightFrom);
    }

    private static <T extends ReadModel> Specification<T> inRoomsNumber(String typeStr, Set<Integer> roomsNumber) {
        if (roomsNumber == null || roomsNumber.isEmpty()) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                root.get(typeStr).get(PHYSICAL).get("roomsNumber").in(roomsNumber);
    }
}
