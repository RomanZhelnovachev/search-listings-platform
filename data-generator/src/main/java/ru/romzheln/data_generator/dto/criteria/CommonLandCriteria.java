package ru.romzheln.data_generator.dto.criteria;

import lombok.Builder;

import java.util.Set;

@Builder
public record CommonLandCriteria(

        Set<Long> landUseIds

) {}
