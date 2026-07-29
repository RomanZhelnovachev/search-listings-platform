package ru.romzheln.listing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romzheln.listing.model.entity.common.LandUse;

public interface LandUseRepository extends JpaRepository<LandUse, Long> {
}
