package ru.romzheln.listing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romzheln.listing.model.entity.owner.OwnerDetails;

public interface OwnerDetailsRepository extends JpaRepository<OwnerDetails, Long> {
}
