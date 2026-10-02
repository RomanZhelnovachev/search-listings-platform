package ru.romzheln.data_generator.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;
import ru.romzheln.data_generator.enums.*;

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
    LayoutType layoutType) {}
