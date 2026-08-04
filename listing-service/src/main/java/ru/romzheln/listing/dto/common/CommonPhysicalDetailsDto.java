package ru.romzheln.listing.dto.common;

import lombok.Builder;
import ru.romzheln.listing.model.enums.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record CommonPhysicalDetailsDto(

        Integer roomsNumber,

        BigDecimal ceilingHeight,

        Renovation renovation,

        Bathroom bathroom,

        WallMaterial material,

        LocalDate completionDate,

        Integer yearBuilt,

        Integer floorsNumber,

        WindowView view,

        Balcony balcony,

        WindowType windowType,

        WindowMaterial windowMaterial,

        LayoutFeature layoutFeature,

        LayoutType layoutType

){
        }
