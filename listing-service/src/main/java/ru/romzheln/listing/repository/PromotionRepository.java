package ru.romzheln.listing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romzheln.listing.model.entity.listing.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}
