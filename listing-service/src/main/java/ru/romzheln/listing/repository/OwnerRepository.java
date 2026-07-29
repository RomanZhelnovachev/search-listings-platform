package ru.romzheln.listing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romzheln.listing.model.entity.owner.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
