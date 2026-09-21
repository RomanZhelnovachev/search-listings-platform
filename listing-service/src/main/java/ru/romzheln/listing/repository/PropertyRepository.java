package ru.romzheln.listing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romzheln.listing.model.entity.property.Property;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByApartmentDeveloperId(Long developerId);

    List<Property> findByHouseDeveloperId(Long developerId);

    List<Property> findByApartmentComplexId(Long complexId);

    List<Property> findByHouseComplexId(Long complexId);

  List<Property> findByHouseCommonLandDetailsLandUseId(Long landUseId);

  List<Property> findByLandPlotCommonLandDetailsLandUseId(Long landUseId);
}
