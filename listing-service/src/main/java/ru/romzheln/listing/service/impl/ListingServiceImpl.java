package ru.romzheln.listing.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romzheln.listing.dto.event.listing.*;
import ru.romzheln.listing.dto.request.listing.CreateListingRequest;
import ru.romzheln.listing.dto.request.listing.UpdateListingRequest;
import ru.romzheln.listing.dto.response.ListingResponse;
import ru.romzheln.listing.exception.ListingNotFoundByIdException;
import ru.romzheln.listing.exception.UpdateListingException;
import ru.romzheln.listing.mapper.ListingMapper;
import ru.romzheln.listing.model.entity.listing.Image;
import ru.romzheln.listing.model.entity.listing.Listing;
import ru.romzheln.listing.model.entity.listing.MortgageProgram;
import ru.romzheln.listing.model.entity.listing.Promotion;
import ru.romzheln.listing.model.entity.owner.Owner;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.model.enums.AggregateType;
import ru.romzheln.listing.model.enums.DealType;
import ru.romzheln.listing.model.enums.EventType;
import ru.romzheln.listing.model.enums.ListingStatus;
import ru.romzheln.listing.repository.ListingRepository;
import ru.romzheln.listing.service.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListingServiceImpl implements ListingService {

    private final ListingRepository listingRepository;
    private final OutboxEventService outboxEventService;
    private final OwnerService ownerService;
    private final PropertyService propertyService;
    private final PromotionService promotionService;
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
        log.info("Объявление с ID {} успешно сохранено",
                savedListing.getId());
        return listingMapper.toResponse(savedListing);
    }

    @Override
    @Transactional
    public ListingResponse updateListing(UpdateListingRequest request) {
        Listing listing = getListing(request.id());
        String title = request.title();
        String description = request.description();
        DealType dealType = request.dealType();
        if (title == null && description == null && dealType == null) {
            log.warn("Плохой запрос на обновление объявления с ID {} - нет полей для обновления",
                    listing.getId());
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
        outboxEventService.save(AggregateType.LISTING,
                listing.getId(),
                EventType.UPDATED,
                listingMapper.toListingUpdatedEvent(listing));
        log.info("Объявление с ID {} успешно обновлено",
                listing.getId());
        return listingMapper.toResponse(listing);
    }

    @Override
    @Transactional
    public ListingResponse changePrice(Long id,
                                       BigDecimal price) {
        Listing listing = getListing(id);
        BigDecimal oldPrice = listing.changePrice(price);
        outboxEventService.save(AggregateType.LISTING,
                listing.getId(),
                EventType.PRICE_CHANGED,
                new ChangePriceEvent(oldPrice, price));
        log.info("Цена в объявление с ID {} успешно изменена",
                listing.getId());
        return listingMapper.toResponse(listing);
    }

    @Override
    @Transactional
    public void assignPromotion(Long id,
                             Long promotionId) {
        Listing listing = getListing(id);
        Promotion promotion = promotionService.getPromotionById(promotionId);
        listing.assignPromotion(promotion);
        outboxEventService.save(AggregateType.LISTING,
                id,
                EventType.PROMOTION_ADDED,
                new PromotionAddedEvent(promotionId));
        log.info("Объявлению с ID {} добавлена промоакция - {}",
                id,
                promotion.getName());
    }

    @Override
    @Transactional
    public void disablePromotion(Long id) {
        Listing listing = getListing(id);
        listing.disablePromotion();
        outboxEventService.save(AggregateType.LISTING, id, EventType.PROMOTION_DISABLED, new PromotionDisabledEvent());
        log.info("У объявления с ID {} отключена промоакция", id);
    }

    @Override
    @Transactional
    public void addMortgagePrograms(Long id,
                                   Set<MortgageProgram> mortgagePrograms) {
        Listing listing = getListing(id);
        listing.addMortgagePrograms(mortgagePrograms);
        Set<Long> programsIds = new HashSet<>();
        for (MortgageProgram mp : mortgagePrograms) {
            programsIds.add(mp.getId());
            log.info("Объявлению с ID {} добавлена ипотечная программа - {}",
                    id,
                    mp.getName());
        }
        outboxEventService.save(AggregateType.LISTING,
                id,
                EventType.MORTGAGE_PROGRAM_ADDED,
                new MortgageProgramsAddedEvent(programsIds));
    }

    @Override
    @Transactional
    public void removeMortgagePrograms(Long id,
                                       Set<MortgageProgram> mortgagePrograms) {
        Listing listing = getListing(id);
        listing.removeMortgagePrograms(mortgagePrograms);
        outboxEventService.save(AggregateType.LISTING, id, EventType.MORTGAGE_PROGRAMS_REMOVED, new MotgageProgramRemovedEvent(mortgagePrograms));
        for(MortgageProgram mp : mortgagePrograms){
            log.info("В объявлении с ID {} отключена ипотечная программа - {} - {}", id, mp.getId(), mp.getName());
        }
    }

    @Override
    @Transactional
    public void publishListing(Long id) {
        Listing listing = getListing(id);
        listing.publish();
        outboxEventService.save(AggregateType.LISTING, id, EventType.PUBLISHED, new ListingPublishedEvent());
        log.info("Объявление с Id {} успешно опубликовано", id);
    }

    @Override
    @Transactional
    public void archiveListing(Long id) {
        Listing listing = getListing(id);
        listing.archive();
        outboxEventService.save(AggregateType.LISTING, id, EventType.ARCHIVED, new ListingArchivedEvent());
        log.info("Объявление с Id {} успешно заархивировано", id);
    }

    @Override
    @Transactional
    public void approveListing(Long id) {
        Listing listing = getListing(id);
        listing.approve();
        outboxEventService.save(AggregateType.LISTING, id, EventType.APPROVED, new ListingApprovedEvent());
        log.info("Объявление с Id {} успешно прошло модерацию", id);
    }

    @Override
    @Transactional
    public void addImages(Long id,
                          Set<Image> images) {
        Listing listing = getListing(id);
        Set<Image> newImages = listing.addImages(images);
        outboxEventService.save(AggregateType.LISTING, id, EventType.IMAGES_ADDED, new ImageAddedEvent(images));
        log.info("Объявлению с ID {} добавлено {} изображений", id, newImages.size());
    }

    @Override
    @Transactional
    public void removeImages(Long id,
                             Set<Image> images) {
        Listing listing = getListing(id);
        listing.removeImages(images);
        outboxEventService.save(AggregateType.LISTING, id, EventType.IMAGES_REMOVED, new ImageRemovedEvent(images));
        log.info("В объявлении с ID {} удалено {} изображений", id, images.size());
    }

    @Override
    @Transactional(readOnly = true)
    public ListingResponse findListingById(Long id) {
        return listingMapper.toResponse(getListing(id));

    }

    @Override
    @Transactional
    public void deleteListing(Long id, String reason) {
        Listing listing = getListing(id);
        listing.remove();
        outboxEventService.save(AggregateType.LISTING, id, EventType.REMOVED, new ListingRemovedEvent(reason));
        log.info("Объявление с Id {} успешно удалено", id);
    }

    private Listing getListing(Long id) {
        return listingRepository.findById(id)
                .orElseThrow(() -> new ListingNotFoundByIdException(id));
    }
}