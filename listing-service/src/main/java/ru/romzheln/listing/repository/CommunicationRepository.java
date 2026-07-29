package ru.romzheln.listing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romzheln.listing.model.entity.common.Communication;

public interface CommunicationRepository extends JpaRepository<Communication, Long> {
}
