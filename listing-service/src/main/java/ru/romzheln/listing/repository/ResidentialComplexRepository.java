package ru.romzheln.listing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romzheln.listing.model.entity.common.ResidentialComplex;

public interface ResidentialComplexRepository extends JpaRepository<ResidentialComplex, Long> {
}
