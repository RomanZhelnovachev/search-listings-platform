package ru.romzheln.search_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romzheln.search_service.model.read_model.ListingApartmentReadModel;

public interface ListingApartmentReadModelRepository extends JpaRepository<ListingApartmentReadModel, Long> {}
