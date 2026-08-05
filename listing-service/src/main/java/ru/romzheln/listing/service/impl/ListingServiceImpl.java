package ru.romzheln.listing.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romzheln.listing.dto.request.listing.*;
import ru.romzheln.listing.dto.event.OutboxPayload;
import ru.romzheln.listing.dto.event.listing.*;
import ru.romzheln.listing.dto.response.ListingResponse;
import ru.romzheln.listing.exception.ListingNotFoundByIdException;
import ru.romzheln.listing.exception.UpdateListingException;
import ru.romzheln.listing.mapper.ListingMapper;
import ru.romzheln.listing.model.entity.listing.Listing;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.model.enums.AggregateType;
import ru.romzheln.listing.model.enums.DealType;
import ru.romzheln.listing.model.enums.EventType;
import ru.romzheln.listing.model.enums.ListingStatus;
import ru.romzheln.listing.repository.ListingRepository;
import ru.romzheln.listing.service.*;

import java.math.BigDecimal;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListingServiceImpl implements ListingService {

    private final ListingRepository listingRepository;
    private final OutboxEventService outboxEventService;
    private final ListingMapper mapper;
    private final PropertyService propertyService;


    @Override
    @Transactional
    public ListingResponse createListing(CreateListingRequest request) {
        Property property = propertyService.findPropertyById(request.propertyId());
        Listing listing = Listing.builder()
                .title(request.title())
                .description(request.description())
                .status(ListingStatus.CREATED)
                .ownerId(request.ownerId())
                .property(property)
                .dealType(request.dealType())
                .price(request.price())
                .build();
        Listing savedListing = listingRepository.save(listing);
        publishEvent(savedListing.getId(), EventType.CREATED,
                mapper.toListingCreatedEvent(savedListing));
        log.info("Объявление с ID {} успешно сохранено",
                savedListing.getId());
        return mapper.toResponse(savedListing);
    }

    @Override
    @Transactional
    public ListingResponse updateListing(Long id, UpdateListingRequest request) {
        Listing listing = getListing(id);
        String title = request.title();
        String description = request.description();
        DealType dealType = request.dealType();
        if (title == null && description == null && dealType == null) {
            log.warn("Плохой запрос на обновление объявления с ID {} - нет полей для обновления", listing.getId());
            throw new UpdateListingException(listing.getId());
        }
        if (title != null) {
            listing.changeTitle(title);
        }
        if (description != null) {
            listing.changeDescription(description);
        }
        if (dealType != null) {
            listing.changeDealType(dealType);
        }
        publishEvent(listing.getId(), EventType.UPDATED, mapper.toListingUpdatedEvent(listing));
        log.info("Объявление с ID {} успешно обновлено",
                listing.getId());
        return mapper.toResponse(listing);
    }

    @Override
    @Transactional
    public ListingResponse changePrice(Long id,
                                       ChangePriceRequest request) {
        Listing listing = getListing(id);
        BigDecimal oldPrice = listing.changePrice(request.newPrice());
        publishEvent(listing.getId(), EventType.PRICE_CHANGED, new ChangePriceEvent(oldPrice, request.newPrice()));
        log.info("Цена в объявление с ID {} успешно изменена",
                listing.getId());
        return mapper.toResponse(listing);
    }

    @Override
    @Transactional
    public void assignPromotion(Long id, ChangeListingPromotionRequest request) {
        Listing listing = getListing(id);
        listing.assignPromotion(request.promotionId());
        publishEvent(id, EventType.PROMOTION_ADDED, new PromotionAddedEvent(request.promotionId()));
        log.info("Объявлению с ID {} добавлена промоакция - {}",
                id,
                request.promotionId());
    }

    @Override
    @Transactional
    public void disablePromotion(Long id) {
        Listing listing = getListing(id);
        listing.disablePromotion();
        publishEvent(id, EventType.PROMOTION_DISABLED, new PromotionDisabledEvent());
        log.info("У объявления с ID {} отключена промоакция", id);
    }

    @Override
    @Transactional
    public void addMortgagePrograms(Long id, ChangeListingMortgageProgramsRequest request) {
        Listing listing = getListing(id);
        listing.addMortgagePrograms(request.mortgageProgramIds());
        log.info("Объявлению с ID {} добавлены следующие ипотечные программы - {}",
                    id, request.mortgageProgramIds());
        publishEvent(id, EventType.MORTGAGE_PROGRAM_ADDED, new MortgageProgramsAddedEvent(request.mortgageProgramIds()));
    }

    @Override
    @Transactional
    public void removeMortgagePrograms(Long id, ChangeListingMortgageProgramsRequest request) {
        Listing listing = getListing(id);
        listing.removeMortgagePrograms(request.mortgageProgramIds());
        publishEvent(id, EventType.MORTGAGE_PROGRAMS_REMOVED, new MotgageProgramRemovedEvent(request.mortgageProgramIds()));
        log.info("В объявлении с ID {} отключены следующие ипотечные программы - {} ", id, request.mortgageProgramIds());

    }

    @Override
    @Transactional
    public void publishListing(Long id) {
        Listing listing = getListing(id);
        listing.publish();
        publishEvent(id, EventType.PUBLISHED, new ListingPublishedEvent());
        log.info("Объявление с Id {} успешно опубликовано", id);
    }

    @Override
    @Transactional
    public void archiveListing(Long id) {
        Listing listing = getListing(id);
        listing.archive();
        publishEvent(id, EventType.ARCHIVED, new ListingArchivedEvent());
        log.info("Объявление с Id {} успешно заархивировано", id);
    }

    @Override
    @Transactional
    public void approveListing(Long id) {
        Listing listing = getListing(id);
        listing.approve();
        publishEvent(id, EventType.APPROVED, new ListingApprovedEvent());
        log.info("Объявление с Id {} успешно прошло модерацию", id);
    }

    @Override
    @Transactional
    public void addImages(Long id, ChangeListingImageRequest request) {
        Listing listing = getListing(id);
        Set<Long> newImages = listing.addImages(request.imageIds());
        publishEvent(id, EventType.IMAGES_ADDED, new ImageAddedEvent(newImages));
        log.info("Объявлению с ID {} добавлено {} изображений", id, newImages.size());
    }

    @Override
    @Transactional
    public void removeImages(Long id, ChangeListingImageRequest request) {
        Listing listing = getListing(id);
        listing.removeImages(request.imageIds());
        publishEvent(id, EventType.IMAGES_REMOVED, new ImageRemovedEvent(request.imageIds()));
        log.info("В объявлении с ID {} удалены следующие изображения {}", id, request.imageIds());
    }

    @Override
    @Transactional(readOnly = true)
    public ListingResponse findListingById(Long id) {
        return mapper.toResponse(getListing(id));

    }

    @Override
    @Transactional
    public void deleteListing(Long id, RemoveListingRequest request) {
        Listing listing = getListing(id);
        listing.remove();
        publishEvent(id, EventType.REMOVED, new ListingRemovedEvent(request.reason()));
        log.info("Объявление с Id {} успешно удалено", id);
    }

    private Listing getListing(Long id) {
        return listingRepository.findById(id)
                .orElseThrow(() -> new ListingNotFoundByIdException(id));
    }

    private void publishEvent(Long id, EventType type, OutboxPayload payload) {
        outboxEventService.save(AggregateType.LISTING, id, type, payload);
    }
}