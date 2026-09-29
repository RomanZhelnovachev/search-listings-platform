package ru.romzheln.search_service.mapper.responseMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.romzheln.search_service.dto.response.ApartmentResponse;
import ru.romzheln.search_service.dto.response.CommercialResponse;
import ru.romzheln.search_service.dto.response.HouseResponse;
import ru.romzheln.search_service.dto.response.LandPlotResponse;
import ru.romzheln.search_service.model.read_model.ListingApartmentReadModel;
import ru.romzheln.search_service.model.read_model.ListingCommercialReadModel;
import ru.romzheln.search_service.model.read_model.ListingHouseReadModel;
import ru.romzheln.search_service.model.read_model.ListingLandPlotReadModel;

@Mapper(componentModel = "spring", uses = CommonResponseMapper.class)
public interface SearchResponseMapper {

  @Mapping(source = "apartment.apartmentType", target = "apartmentType")
  @Mapping(source = "apartment.commonPhysicalDetails", target = "commonPhysicalDetails")
  @Mapping(source = "apartment.apartmentPhysicalDetails.kitchenSquare", target = "kitchenSquare")
  @Mapping(source = "apartment.apartmentPhysicalDetails.floor", target = "floor")
  @Mapping(source = "apartment.apartmentPhysicalDetails.elevator", target = "elevator")
  @Mapping(source = "apartment.apartmentPhysicalDetails.ramp", target = "ramp")
  @Mapping(source = "apartment.apartmentPhysicalDetails.side", target = "side")
  @Mapping(source = "apartment.developer.developerName", target = "developerName")
  @Mapping(source = "apartment.complex.complexName", target = "complexName")
  ApartmentResponse toApartmentResponse(ListingApartmentReadModel model);

  @Mapping(source = "commercial.commonPhysicalDetails", target = "commonPhysicalDetails")
  @Mapping(source = "commercial.commercialPhysicalDetails.floor", target = "floor")
  @Mapping(source = "commercial.commercialPhysicalDetails.line", target = "line")
  @Mapping(
      source = "commercial.commercialPhysicalDetails.propertyLocationType",
      target = "propertyLocationType")
  @Mapping(
      source = "commercial.commercialPhysicalDetails.territorialZone",
      target = "territorialZone")
  @Mapping(
      source = "commercial.commercialPhysicalDetails.separateEntrance",
      target = "separateEntrance")
  @Mapping(source = "commercial.commercialPhysicalDetails.ventilation", target = "ventilation")
  @Mapping(source = "commercial.commercialPhysicalDetails.tenantExists", target = "tenantExists")
  @Mapping(
      source = "commercial.commercialPhysicalDetails.entrancesNumber",
      target = "entrancesNumber")
  @Mapping(
      source = "commercial.commercialPhysicalDetails.electricalPowerKw",
      target = "electricalPowerKw")
  @Mapping(
      source = "commercial.commercialPhysicalDetails.railwayDeadEnd",
      target = "railwayDeadEnd")
  @Mapping(source = "commercial.purposeIds", target = "purposeIds")
  CommercialResponse toCommercialResponse(ListingCommercialReadModel model);

  @Mapping(source = "house.commonPhysicalDetails", target = "commonPhysicalDetails")
  @Mapping(source = "house.commonLandDetails", target = "commonLandDetails")
  @Mapping(source = "house.developer.developerName", target = "developerName")
  @Mapping(source = "house.complex.complexName", target = "complexName")
  @Mapping(source = "house.constructionStage", target = "constructionStage")
  @Mapping(source = "house.additionalBuildings", target = "additionalBuildings")
  @Mapping(source = "house.landPlotSquare", target = "landPlotSquare")
  HouseResponse toHouseResponse(ListingHouseReadModel model);

  @Mapping(source = "landPlot.commonLandDetails", target = "commonLandDetails")
  @Mapping(source = "landPlot.additionalBuildings", target = "additionalBuildings")
  LandPlotResponse toLandPlotResponse(ListingLandPlotReadModel model);
}
