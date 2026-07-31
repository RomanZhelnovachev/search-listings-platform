package ru.romzheln.listing.service.strategy;

import ru.romzheln.listing.dto.request.property.CreatePropertyRequest;
import ru.romzheln.listing.dto.request.property.UpdatePropertyRequest;
import ru.romzheln.listing.model.entity.property.Property;

public interface PropertyStrategy {

    Property create(CreatePropertyRequest request);

    Property update(UpdatePropertyRequest request);

}
