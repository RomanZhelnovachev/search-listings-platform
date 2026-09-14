package ru.romzheln.listing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romzheln.listing.model.entity.property.Property;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findPropertyByDeveloperId(Long developerId);

    List<Property> findPropertyByLandUseId(Long landUseId);

    List<Property> findPropertyByComplexId(Long complexId);
}
