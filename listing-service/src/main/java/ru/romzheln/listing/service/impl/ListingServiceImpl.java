package ru.romzheln.listing.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romzheln.listing.dto.event.listing.ChangePriceEvent;
import ru.romzheln.listing.dto.request.CreateListingRequest;
import ru.romzheln.listing.dto.request.UpdateListingRequest;
import ru.romzheln.listing.dto.response.ListingResponse;
import ru.romzheln.listing.exception.ListingNotFoundByIdException;
import ru.romzheln.listing.exception.UpdateListingException;
import ru.romzheln.listing.mapper.ListingMapper;
import ru.romzheln.listing.model.entity.listing.Listing;
import ru.romzheln.listing.model.entity.listing.MortgageProgram;
import ru.romzheln.listing.model.entity.owner.Owner;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.model.enums.AggregateType;
import ru.romzheln.listing.model.enums.DealType;
import ru.romzheln.listing.model.enums.EventType;
import ru.romzheln.listing.model.enums.ListingStatus;
import ru.romzheln.listing.repository.ListingRepository;
import ru.romzheln.listing.service.ListingService;
import ru.romzheln.listing.service.OutboxEventService;
import ru.romzheln.listing.service.OwnerService;
import ru.romzheln.listing.service.PropertyService;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListingServiceImpl implements ListingService {

    private final ListingRepository listingRepository;

    private final OutboxEventService outboxEventService;

    private final OwnerService ownerService;

    private final PropertyService propertyService;

    private final ListingMapper listingMapper;


    @Override
    @Transactional
    public ListingResponse createListing(CreateListingRequest request) {
        Owner owner = ownerService.findOwnerById(request.ownerId());
        Property property = propertyService.findPropertyById(request.propertyId());
        Listing listing = Listing.builder()
                .title(request.title())
                .description(request.description())
                .status(ListingStatus.CREATED)
                .owner(owner)
                .property(property)
                .dealType(request.dealType())
                .price(request.price())
                .build();
        Listing savedListing = listingRepository.save(listing);
        outboxEventService.save(AggregateType.LISTING,
                savedListing.getId(),
                EventType.CREATED,
                listingMapper.toListingCreatedEvent(savedListing));
        log.info("Объявление с ID {} успешно сохранено", savedListing.getId());
        return listingMapper.toResponse(savedListing);
    }

    @Override
    @Transactional
    public ListingResponse updateListing(UpdateListingRequest request) {
        Listing listing = getListing(request.id());
        String title = request.title();
        String description = request.description();
        DealType dealType = request.dealType();
        if (title == null && description == null && dealType == null){
            log.warn("Плохой запрос на обновление объявления с ID {} - нет полей для обновления", listing.getId());
            throw new UpdateListingException(listing.getId());
        }
        if(title != null){
            listing.changeTitle(title);
        }
        if(description != null){
            listing.changeDescription(description);
        }
        if(dealType != null){
            listing.changeDealType(dealType);
        }
        outboxEventService.save(AggregateType.LISTING, listing.getId(), EventType.UPDATED, listingMapper.toListingUpdatedEvent(listing));
        log.info("Объявление с ID {} успешно обновлено", listing.getId());
        return listingMapper.toResponse(listing);
    }

    @Override
    @Transactional
    public ListingResponse changePrice(Long id,
                                       BigDecimal price) {
        Listing listing = getListing(id);
        ChangePriceEvent event = listing.changePrice(price);
        outboxEventService.save(AggregateType.LISTING,
                listing.getId(), EventType.UPDATED, event);
        log.info("Цена в объявление с ID {} успешно изменена", listing.getId());
        return listingMapper.toResponse(listing);
    }

    @Override
    public void addPromotion(Long id,
                             Long promotionId) {

    }

    @Override
    public void addMortgageProgram(Long id,
                                   Set<MortgageProgram> mortgagePrograms) {

    }

    @Override
    @Transactional
    public void publishListing(Long id) {

    }

    @Override
    @Transactional
    public void archiveListing(Long id) {

    }

    @Override
    @Transactional(readOnly = true)
    public ListingResponse findListingById(Long id) {
        return listingMapper.toResponse(getListing(id));

    }

    @Override
    @Transactional
    public void deleteListing(Long id) {

    }

    private Listing getListing(Long id){
        return listingRepository.findById(id).orElseThrow(()-> new ListingNotFoundByIdException(id));
    }
}


