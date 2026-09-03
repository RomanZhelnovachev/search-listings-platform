package ru.romzheln.search_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romzheln.search_service.model.projection.CommunicationProjection;

public interface CommunicationProjectionRepository extends JpaRepository<CommunicationProjection, Long> {}
