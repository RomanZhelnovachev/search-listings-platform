package ru.romzheln.listing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romzheln.listing.model.entity.common.AdditionalBuilding;

public interface AdditionalBuildingRepository extends JpaRepository<AdditionalBuilding, Long> {
}
