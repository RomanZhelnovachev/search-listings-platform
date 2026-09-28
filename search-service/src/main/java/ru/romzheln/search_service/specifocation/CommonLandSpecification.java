package ru.romzheln.search_service.specifocation;

import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.romzheln.search_service.dto.criteria.CommonLandCriteria;
import ru.romzheln.search_service.exception.UnsupportedFieldException;
import ru.romzheln.search_service.model.enums.PropertyType;
import ru.romzheln.search_service.model.read_model.ReadModel;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonLandSpecification {

    public static <T extends ReadModel>Specification<T> getCommonLandSpecification(CommonLandCriteria criteria, PropertyType type){
        if(criteria == null){
            return null;
        }
        String typeStr = null;
        switch (type){
            case HOUSE -> typeStr = "house";
            case LAND_PLOT -> typeStr = "landPlot";
            case APARTMENT -> throw new UnsupportedFieldException("У Apartment нет поля CommonLandDetails");
            case COMMERCIAL -> throw new UnsupportedFieldException("У Commercial нет поля CommonLandDetails");
        }
        return Specification.allOf(
                iiLandUseIds(typeStr, criteria.landUseIds())
        );
    }

    private static <T extends ReadModel> Specification<T> iiLandUseIds(String typeStr, Set<Long> landUseIds) {
        if(landUseIds == null || landUseIds.isEmpty()){
            return null;
        }
        return (root,query,criteriaBuilder) -> 
                root.get(typeStr).get("commonLandDetails").get("landUseId").in(landUseIds);
    }
}
