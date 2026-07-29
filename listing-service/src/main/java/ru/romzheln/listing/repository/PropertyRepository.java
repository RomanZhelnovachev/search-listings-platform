package ru.romzheln.listing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romzheln.listing.model.entity.property.Property;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
