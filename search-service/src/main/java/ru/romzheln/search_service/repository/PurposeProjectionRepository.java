package ru.romzheln.search_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romzheln.search_service.model.projection.PurposeProjection;

public interface PurposeProjectionRepository extends JpaRepository<PurposeProjection, Long> {}
