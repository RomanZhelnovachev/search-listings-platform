package ru.romzheln.data_generator.dto.criteria;

import lombok.Builder;
import ru.romzheln.data_generator.enums.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Builder
public record CommonPhysicalCriteria(

        Set<Integer> roomsNumber,

        BigDecimal ceilingHeightFrom,

        BigDecimal ceilingHeightTo,

        Set<Renovation> renovations,

        Set<Bathroom> bathrooms,

        Set<WallMaterial> wallMaterials,

        LocalDate completionDate,

        Integer yearBuiltFrom,

        Integer yearBuiltTo,

        Integer floorsNumberFrom,

        Integer floorsNumberTo,

        Set<WindowView> views,

        Set<Balcony> balconies,

        Set<WindowType> windowTypes,

        Set<WindowMaterial> windowMaterials,

        Set<LayoutFeature> layoutFeatures,

        Set<LayoutType> layoutTypes

) {}
