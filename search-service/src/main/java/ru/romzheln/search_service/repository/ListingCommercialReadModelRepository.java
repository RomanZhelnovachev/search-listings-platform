package ru.romzheln.search_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romzheln.search_service.model.read_model.ListingCommercialReadModel;

public interface ListingCommercialReadModelRepository extends JpaRepository<ListingCommercialReadModel, Long> {}
