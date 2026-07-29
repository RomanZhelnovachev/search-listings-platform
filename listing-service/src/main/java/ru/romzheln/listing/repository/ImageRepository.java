package ru.romzheln.listing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romzheln.listing.model.entity.listing.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
