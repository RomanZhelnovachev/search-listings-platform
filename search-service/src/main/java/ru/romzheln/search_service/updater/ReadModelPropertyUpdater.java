package ru.romzheln.search_service.updater;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.search_service.dto.event.common.ApartmentPhysicalDetailsDto;
import ru.romzheln.search_service.dto.event.common.CommercialPhysicalDetailsDto;
import ru.romzheln.search_service.dto.event.common.CommonLandDetailsDto;
import ru.romzheln.search_service.dto.event.common.CommonPhysicalDetailsDto;
import ru.romzheln.search_service.dto.event.property.*;
import ru.romzheln.search_service.model.embeded.Property;
import ru.romzheln.search_service.model.read_model.*;

@Component
@RequiredArgsConstructor
public class ReadModelPropertyUpdater {

  private final PropertyCommonFieldsUpdater commonFieldsUpdater;

  public void updateApartment(ListingApartmentReadModel model, ApartmentEvent event) {
      commonFieldsUpdater.updateCommonFields(getProperty(model), event);
    CommonPhysicalDetailsDto commonPhysicalDetailsDto = event.getCommonPhysicalDetailsDto();
    if (commonPhysicalDetailsDto != null) {
      commonFieldsUpdater.updateCommonPhysicalDetails(
          model.getApartment().getCommonPhysicalDetails(), commonPhysicalDetailsDto);
    }
    ApartmentPhysicalDetailsDto apartmentPhysicalDetailsDto =
        event.getApartmentPhysicalDetailsDto();
    if (apartmentPhysicalDetailsDto != null) {
      commonFieldsUpdater.updateApartmentPhysicalDetails(
          model.getApartment().getApartmentPhysicalDetails(), apartmentPhysicalDetailsDto);
    }
    commonFieldsUpdater.updateDeveloper(
        model.getApartment().getDeveloper(), event.getDeveloperId(), event.getDeveloperName());
    commonFieldsUpdater.updateComplex(
        model.getApartment().getComplex(), event.getComplexId(), event.getComplexName());
  }

  public void updateCommercial(ListingCommercialReadModel model, CommercialEvent event) {
      commonFieldsUpdater.updateCommonFields(getProperty(model), event);
      CommonPhysicalDetailsDto commonPhysicalDetailsDto = event.getCommonPhysicalDetailsDto();
      if (commonPhysicalDetailsDto != null) {
          commonFieldsUpdater.updateCommonPhysicalDetails(
                  model.getCommercial().getCommonPhysicalDetails(), commonPhysicalDetailsDto);
      }
      CommercialPhysicalDetailsDto commercialPhysicalDetailsDto = event.getCommercialPhysicalDetailsDto();
      if(commercialPhysicalDetailsDto != null){
          commonFieldsUpdater.updateCommercialPhysicalDetails(model.getCommercial().getCommercialPhysicalDetails(), commercialPhysicalDetailsDto);
      }
      if(event.getPurposesIds() != null &&!event.getPurposesIds().isEmpty()){
         model.getCommercial().setPurposeIds(event.getPurposesIds());
      }
  }

  public void updateHouse(ListingHouseReadModel model, HouseEvent event) {
      commonFieldsUpdater.updateCommonFields(getProperty(model), event);
      CommonPhysicalDetailsDto commonPhysicalDetailsDto = event.getCommonPhysicalDetailsDto();
      if (commonPhysicalDetailsDto != null) {
          commonFieldsUpdater.updateCommonPhysicalDetails(
                  model.getHouse().getCommonPhysicalDetails(), commonPhysicalDetailsDto);
      }
      CommonLandDetailsDto commonLandDetailsDto = event.getCommonLandDetailsDto();
      if(commonLandDetailsDto != null){
          commonFieldsUpdater.updateCommonLandDetails(model.getHouse().getCommonLandDetails(), commonLandDetailsDto);
      }
    commonFieldsUpdater.updateDeveloper(
        model.getHouse().getDeveloper(), event.getDeveloperId(), event.getDeveloperName());
      commonFieldsUpdater.updateComplex(model.getHouse().getComplex(), event.getComplexId(), event.getComplexName());
      if(event.getConstructionStage() != null){
          model.getHouse().setConstructionStage(event.getConstructionStage());
      }
      if(event.getAdditionalBuildings() != null && !event.getAdditionalBuildings().isEmpty()){
          model.getHouse().setAdditionalBuildings(event.getAdditionalBuildings());
      }
      if(event.getLandPlotSquare() != null){
          model.getHouse().setLandPlotSquare(event.getLandPlotSquare());
      }
  }

  public void updateLandPlot(ListingLandPlotReadModel model, LandPlotEvent event) {
      commonFieldsUpdater.updateCommonFields(getProperty(model), event);
      CommonLandDetailsDto commonLandDetailsDto = event.getCommonLandDetailsDto();
      if(commonLandDetailsDto != null){
          commonFieldsUpdater.updateCommonLandDetails(model.getLandPlot().getCommonLandDetails(), commonLandDetailsDto);
      }
      if(event.getAdditionalBuildings() != null && !event.getAdditionalBuildings().isEmpty()){
          model.getLandPlot().setAdditionalBuildings(event.getAdditionalBuildings());
      }
  }

  private Property getProperty(ReadModel model) {
    return model.getListing().getProperty();
  }
}
