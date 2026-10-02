package ru.romzheln.data_generator.dto.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Getter;
import ru.romzheln.data_generator.dto.LocationDto;
import ru.romzheln.data_generator.enums.Own;
import ru.romzheln.data_generator.enums.PropertyType;


@Getter
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "propertyType",
    visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = CreateApartmentResponse.class, name = "APARTMENT"),
  @JsonSubTypes.Type(value = CreateHouseResponse.class, name = "HOUSE"),
  @JsonSubTypes.Type(value = CreateCommercialResponse.class, name = "COMMERCIAL"),
  @JsonSubTypes.Type(value = CreateLandPlotResponse.class, name = "LAND_PLOT")
})
public abstract class CreatePropertyResponse {

    @NotNull
    private PropertyType propertyType;

  private LocationDto location;

  @Positive private BigDecimal square;

  private Own own;

  private Boolean firstOwner;

  private Set<Long> communicationIds;
}
