package ru.romzheln.search_service.dto.response.commonDto;

import ru.romzheln.search_service.model.enums.Region;

public record LocationDto(

        Region region,

        String populatedArea,

        String street,

        String house,

        String building,

        String apartment
) {}
