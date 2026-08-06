package ru.romzheln.listing.service.strategy.impl;

import org.springframework.stereotype.Component;
import ru.romzheln.listing.dto.request.property.common.CreatePropertyRequest;
import ru.romzheln.listing.dto.request.property.common.UpdatePropertyRequest;
import ru.romzheln.listing.dto.request.property.landPlot.CreateLandPlotRequest;
import ru.romzheln.listing.dto.request.property.landPlot.UpdateLandPlotRequest;
import ru.romzheln.listing.mapper.PhysicalDetailsMapper;
import ru.romzheln.listing.model.entity.common.CommonLandDetails;
import ru.romzheln.listing.model.entity.common.LandUse;
import ru.romzheln.listing.model.entity.landPlot.LandPlot;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.model.enums.PropertyType;
import ru.romzheln.listing.repository.PropertyRepository;
import ru.romzheln.listing.resolver.PropertyReferenceResolver;
import ru.romzheln.listing.service.impl.CommunicationServiceImpl;
import ru.romzheln.listing.service.strategy.AbstractPropertyStrategy;
import ru.romzheln.listing.util.ClassCastUtil;

@Component
public class LandPlotStrategy extends AbstractPropertyStrategy {

    private final PropertyReferenceResolver resolver;
    private final PhysicalDetailsMapper mapper;

    protected LandPlotStrategy(CommunicationServiceImpl communicationService,
                               PropertyRepository propertyRepository,
                               PropertyReferenceResolver resolver,
                               PhysicalDetailsMapper mapper) {
        super(communicationService,
                propertyRepository);
        this.resolver = resolver;
        this.mapper = mapper;
    }

    @Override
    public Property create(CreatePropertyRequest request) {
        Property property = buildProperty(request);
        CreateLandPlotRequest landPlotRequest = ClassCastUtil.requireType(request,
                CreateLandPlotRequest.class);
        LandUse landUse = resolver.getLandUse(landPlotRequest.getCommonLandDetailsDto()
                .landUse());
        CommonLandDetails commonLandDetails = mapper.buildCommonLandDetails(landPlotRequest.getCommonLandDetailsDto(),
                landUse);
        LandPlot landPlot = LandPlot.builder()
                .property(property)
                .commonLandDetails(commonLandDetails)
                .additionalBuildings(resolver.getAllAdditionalBuildingsById(landPlotRequest.getAdditionalBuildings()))
                .build();
        property.setLandPlot(landPlot);
        return property;
    }

    @Override
    public Property update(Long id, UpdatePropertyRequest request) {
        Property property = findPropertyById(id, PropertyType.LAND_PLOT);
        updateProperty(property,request);
        LandPlot landPlot = property.getLandPlot();
        UpdateLandPlotRequest landPlotRequest = ClassCastUtil.requireType(request, UpdateLandPlotRequest.class);
        LandUse landUse = landPlot.getCommonLandDetails().getLandUse();
        if (landPlotRequest.getCommonLandDetailsDto() != null) {
            if (landPlotRequest.getCommonLandDetailsDto().landUse() != null) {
                landUse = resolver.getLandUse(landPlotRequest.getCommonLandDetailsDto().landUse());
            }
            mapper.updateCommonLandDetails(landPlot.getCommonLandDetails(), landPlotRequest.getCommonLandDetailsDto(), landUse);
        }
        if (landPlotRequest.getAdditionalBuildings() != null) {
            landPlot.setAdditionalBuildings(resolver.getAllAdditionalBuildingsById(landPlotRequest.getAdditionalBuildings()));
        }
        return property;
    }
}
