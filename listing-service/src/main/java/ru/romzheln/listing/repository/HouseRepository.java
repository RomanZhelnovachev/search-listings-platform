package ru.romzheln.listing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romzheln.listing.model.entity.house.House;

public interface HouseRepository extends JpaRepository<House, Long> {
}
