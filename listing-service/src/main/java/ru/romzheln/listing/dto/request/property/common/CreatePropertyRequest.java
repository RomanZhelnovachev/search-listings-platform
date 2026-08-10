package ru.romzheln.listing.dto.request.property.common;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import ru.romzheln.listing.dto.common.LocationDto;
import ru.romzheln.listing.dto.request.property.apartment.CreateApartmentRequest;
import ru.romzheln.listing.dto.request.property.commercial.CreateCommercialRequest;
import ru.romzheln.listing.dto.request.property.house.CreateHouseRequest;
import ru.romzheln.listing.dto.request.property.landPlot.CreateLandPlotRequest;
import ru.romzheln.listing.model.enums.Own;
import ru.romzheln.listing.model.enums.PropertyType;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "propertyType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(
                value = CreateApartmentRequest.class,
                name = "APARTMENT"
        ),
        @JsonSubTypes.Type(
                value = CreateHouseRequest.class,
                name = "HOUSE"
        ),
        @JsonSubTypes.Type(
                value = CreateCommercialRequest.class,
                name = "COMMERCIAL"
        ),
        @JsonSubTypes.Type(
                value = CreateLandPlotRequest.class,
                name = "LAND_PLOT"
        )
})
public abstract class CreatePropertyRequest {

    @NotBlank
    private PropertyType propertyType;

    private LocationDto location;

    @Positive
    private BigDecimal square;

    private Own own;

    private Boolean firstOwner;

    private Set<Long> communicationIds;
}
