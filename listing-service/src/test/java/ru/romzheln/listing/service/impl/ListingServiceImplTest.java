package ru.romzheln.listing.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.romzheln.listing.mapper.ListingMapper;
import ru.romzheln.listing.repository.ListingRepository;
import ru.romzheln.listing.service.ListingService;
import ru.romzheln.listing.service.OutboxEventService;
import ru.romzheln.listing.service.PropertyService;

@ExtendWith(MockitoExtension.class)
class ListingServiceImplTest {

    @Mock
    private ListingRepository listingRepository;

    @Mock
    private OutboxEventService outboxEventService;

    @Mock
    private ListingMapper listingMapper;

    @Mock
    private PropertyService propertyService;

    @InjectMocks
    private ListingService listingService;

    @Test
    void createListing() {
    }

    @Test
    void updateListing() {
    }

    @Test
    void changePrice() {
    }

    @Test
    void addPromotion() {
    }

    @Test
    void addMortgageProgram() {
    }

    @Test
    void publishListing() {
    }

    @Test
    void archiveListing() {
    }

    @Test
    void findListingById() {
    }

    @Test
    void deleteListing() {
    }
}