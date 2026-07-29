package ru.romzheln.listing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romzheln.listing.model.entity.apartment.Apartment;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
}
