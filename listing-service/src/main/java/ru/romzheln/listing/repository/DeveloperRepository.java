package ru.romzheln.listing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romzheln.listing.model.entity.common.Developer;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
}
