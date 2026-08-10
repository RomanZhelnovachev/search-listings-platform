package ru.romzheln.listing.dto.request.property.common;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import ru.romzheln.listing.dto.common.LocationDto;
import ru.romzheln.listing.dto.request.property.apartment.UpdateApartmentRequest;
import ru.romzheln.listing.dto.request.property.commercial.UpdateCommercialRequest;
import ru.romzheln.listing.dto.request.property.house.UpdateHouseRequest;
import ru.romzheln.listing.dto.request.property.landPlot.UpdateLandPlotRequest;
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
                value = UpdateApartmentRequest.class,
                name = "APARTMENT"
        ),
        @JsonSubTypes.Type(
                value = UpdateHouseRequest.class,
                name = "HOUSE"
        ),
        @JsonSubTypes.Type(
                value = UpdateCommercialRequest.class,
                name = "COMMERCIAL"
        ),
        @JsonSubTypes.Type(
                value = UpdateLandPlotRequest.class,
                name = "LAND_PLOT"
        )
})
public abstract class UpdatePropertyRequest {

    @NotNull
    private PropertyType propertyType;

    private LocationDto locationDto;

    @Positive
    private BigDecimal square;

    private Own own;

    private Boolean firstOwner;

    private Set<Long> communicationIds;
}
