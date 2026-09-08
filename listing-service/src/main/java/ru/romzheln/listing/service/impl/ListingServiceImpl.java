package ru.romzheln.listing.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romzheln.listing.dto.event.ListingPayload;
import ru.romzheln.listing.dto.event.PropertyPayload;
import ru.romzheln.listing.dto.event.listing.*;
import ru.romzheln.listing.dto.request.listing.*;
import ru.romzheln.listing.dto.response.ListingResponse;
import ru.romzheln.listing.exception.badRequest.UpdateListingException;
import ru.romzheln.listing.exception.notFound.ListingNotFoundByIdException;
import ru.romzheln.listing.mapper.ListingMapper;
import ru.romzheln.listing.mapper.PropertyEventMapper;
import ru.romzheln.listing.model.entity.listing.Listing;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.model.enums.DealType;
import ru.romzheln.listing.model.enums.EventType;
import ru.romzheln.listing.model.enums.ListingStatus;
import ru.romzheln.listing.model.enums.Region;
import ru.romzheln.listing.repository.ListingRepository;
import ru.romzheln.listing.service.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListingServiceImpl implements ListingService {

    private final ListingRepository listingRepository;
    private final OutboxEventService outboxEventService;
    private final ListingMapper listingMapper;
    private final PropertyEventMapper propertyMapper;
    private final PropertyService propertyService;


    @Override
    @Transactional
    public ListingResponse createListing(CreateListingRequest request) {
        Property property = propertyService.getProperty(request.propertyId());
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
        publishEvent(savedListing, EventType.CREATED,
                listingMapper.toListingCreatedEvent(savedListing));
        log.info("Объявление с ID {} успешно сохранено",
                savedListing.getId());
        return listingMapper.toResponse(savedListing);
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
        publishEvent(listing, EventType.UPDATED, listingMapper.toListingUpdatedEvent(listing));
        log.info("Объявление с ID {} успешно обновлено",
                listing.getId());
        return listingMapper.toResponse(listing);
    }

    @Override
    @Transactional
    public void updateProperty(EventType type, Long propertyId, PropertyPayload propertyPayload) {
        List<Listing> listings = listingRepository.findByPropertyId(propertyId);
        if(listings.isEmpty()){
            log.warn("Объявление с объектом недвижимости с ID {} не найдено", propertyId);
            return;
        }
        for(Listing listing : listings){
            log.info("В объявлении с ID {} изменён объект недвижимости", listing.getId());
            Region region = listing.getProperty().getLocation().getRegion();
            outboxEventService.save(listing.getId(), region, type, null, propertyPayload);
        }
    }

    @Override
    @Transactional
    public ListingResponse changePrice(Long id,
                                       ChangePriceRequest request) {
        Listing listing = getListing(id);
        BigDecimal oldPrice = listing.changePrice(request.newPrice());
        publishEvent(listing, EventType.PRICE_CHANGED, new ChangePriceEvent(oldPrice, request.newPrice()));
        log.info("Цена в объявление с ID {} успешно изменена",
                listing.getId());
        return listingMapper.toResponse(listing);
    }

    @Override
    @Transactional
    public void assignPromotion(Long id, ChangeListingPromotionRequest request) {
        Listing listing = getListing(id);
        listing.assignPromotion(request.promotionId());
        publishEvent(listing, EventType.PROMOTION_ADDED, new PromotionAddedEvent(request.promotionId()));
        log.info("Объявлению с ID {} добавлена промоакция - {}",
                id,
                request.promotionId());
    }

    @Override
    @Transactional
    public void disablePromotion(Long id) {
        Listing listing = getListing(id);
        listing.disablePromotion();
        publishEvent(listing, EventType.PROMOTION_DISABLED, new PromotionDisabledEvent());
        log.info("У объявления с ID {} отключена промоакция", id);
    }

    @Override
    @Transactional
    public void addMortgagePrograms(Long id, ChangeListingMortgageProgramsRequest request) {
        Listing listing = getListing(id);
        listing.addMortgagePrograms(request.mortgageProgramIds());
        log.info("Объявлению с ID {} добавлены следующие ипотечные программы - {}",
                    id, request.mortgageProgramIds());
        publishEvent(listing, EventType.MORTGAGE_PROGRAM_ADDED, new MortgageProgramsEvent(request.mortgageProgramIds()));
    }

    @Override
    @Transactional
    public void removeMortgagePrograms(Long id, ChangeListingMortgageProgramsRequest request) {
        Listing listing = getListing(id);
        listing.removeMortgagePrograms(request.mortgageProgramIds());
        publishEvent(listing, EventType.MORTGAGE_PROGRAMS_REMOVED, new MortgageProgramsEvent(request.mortgageProgramIds()));
        log.info("В объявлении с ID {} отключены следующие ипотечные программы - {} ", id, request.mortgageProgramIds());

    }

    @Override
    @Transactional
    public void publishListing(Long id) {
        Listing listing = getListing(id);
        listing.publish();
        publishEvent(listing, EventType.PUBLISHED, new ListingPublishedEvent());
        log.info("Объявление с Id {} успешно опубликовано", id);
    }

    @Override
    @Transactional
    public void archiveListing(Long id) {
        Listing listing = getListing(id);
        listing.archive();
        publishEvent(listing, EventType.ARCHIVED, new ListingArchivedEvent());
        log.info("Объявление с Id {} успешно заархивировано", id);
    }

    @Override
    @Transactional
    public void approveListing(Long id) {
        Listing listing = getListing(id);
        listing.approve();
        publishEvent(listing, EventType.APPROVED, new ListingApprovedEvent());
        log.info("Объявление с Id {} успешно прошло модерацию", id);
    }

    @Override
    @Transactional
    public void addImages(Long id, ChangeListingImageRequest request) {
        Listing listing = getListing(id);
        Set<Long> newImages = listing.addImages(request.imageIds());
        publishEvent(listing, EventType.IMAGES_ADDED, new ImageEvent(newImages));
        log.info("Объявлению с ID {} добавлено {} изображений", id, newImages.size());
    }

    @Override
    @Transactional
    public void removeImages(Long id, ChangeListingImageRequest request) {
        Listing listing = getListing(id);
        listing.removeImages(request.imageIds());
        publishEvent(listing, EventType.IMAGES_REMOVED, new ImageEvent(request.imageIds()));
        log.info("В объявлении с ID {} удалены следующие изображения {}", id, request.imageIds());
    }

    @Override
    @Transactional(readOnly = true)
    public ListingResponse findListingById(Long id) {
        log.info("Получено объявление с ID {}", id);
        return listingMapper.toResponse(getListing(id));

    }

    @Override
    @Transactional(readOnly = true)
    public Page<ListingResponse> getAll(Pageable pageable) {
        log.info("Получен постраничный список всех объявлений");
        return listingMapper.toPageResponse(listingRepository.findAll(pageable));
    }

    @Override
    @Transactional
    public void deleteListing(Long id, RemoveListingRequest request) {
        Listing listing = getListing(id);
        listing.remove();
        publishEvent(listing, EventType.REMOVED, new ListingRemovedEvent(request.reason()));
        log.info("Объявление с Id {} успешно удалено", id);
    }

    private Listing getListing(Long id) {
        return listingRepository.findById(id)
                .orElseThrow(() -> new ListingNotFoundByIdException(id));
    }

    private void publishEvent(Listing listing, EventType type, ListingPayload listingPayload) {
        PropertyPayload propertyPayload = propertyMapper.toPropertyEvent(listing.getProperty());
        Region region = listing.getProperty().getLocation().getRegion();
        outboxEventService.save(listing.getId(), region, type, listingPayload, propertyPayload);
    }
}