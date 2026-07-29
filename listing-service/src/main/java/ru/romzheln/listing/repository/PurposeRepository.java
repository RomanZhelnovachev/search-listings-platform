package ru.romzheln.listing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romzheln.listing.model.entity.commercial.Purpose;

public interface PurposeRepository extends JpaRepository<Purpose, Long> {
}
