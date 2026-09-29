package ru.romzheln.search_service.dto.response.commonDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import ru.romzheln.search_service.model.enums.*;

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
