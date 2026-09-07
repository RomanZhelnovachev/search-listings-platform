package ru.romzheln.search_service.dto.event.common;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record CommonPhysicalDetailsDto(
    Integer roomsNumber,
    BigDecimal ceilingHeight,
    String renovation,
    String bathroom,
    String material,
    LocalDate completionDate,
    Integer yearBuilt,
    Integer floorsNumber,
    String view,
    String balcony,
    String windowType,
    String windowMaterial,
    String layoutFeature,
    String layoutType) {}
