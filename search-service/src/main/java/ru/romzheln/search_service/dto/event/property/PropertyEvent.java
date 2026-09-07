package ru.romzheln.search_service.dto.event.property;

import java.math.BigDecimal;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import ru.romzheln.search_service.dto.event.common.LocationDto;
import ru.romzheln.search_service.model.enums.PropertyType;

@Getter
@Setter
public abstract class PropertyEvent {

  private PropertyType propertyType;

  private LocationDto location;

  private BigDecimal square;

  private String own;

  private Boolean firstOwner;

  private Set<Long> communicationIds;
}
