package ru.romzheln.listing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romzheln.listing.model.entity.commercial.Commercial;

public interface CommercialRepository extends JpaRepository<Commercial, Long> {
}
