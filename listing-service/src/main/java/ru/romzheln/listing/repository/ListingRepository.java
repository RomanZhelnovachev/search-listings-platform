package ru.romzheln.listing.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.romzheln.listing.model.entity.listing.Listing;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    List<Listing> findByPropertyId(Long propertyId);
}
