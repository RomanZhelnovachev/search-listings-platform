package ru.romzheln.listing.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.romzheln.listing.dto.event.listing.ListingCreatedEvent;
import ru.romzheln.listing.dto.event.listing.ListingUpdatedEvent;
import ru.romzheln.listing.dto.event.property.ApartmentEvent;
import ru.romzheln.listing.dto.event.property.PropertyEvent;
import ru.romzheln.listing.dto.request.listing.CreateListingRequest;
import ru.romzheln.listing.dto.request.listing.UpdateListingRequest;
import ru.romzheln.listing.dto.response.ListingResponse;
import ru.romzheln.listing.exception.badRequest.UpdateListingException;
import ru.romzheln.listing.exception.notFound.ListingNotFoundByIdException;
import ru.romzheln.listing.exception.notFound.PropertyNotFoundByIdException;
import ru.romzheln.listing.mapper.ListingMapper;
import ru.romzheln.listing.mapper.PropertyEventMapper;
import ru.romzheln.listing.model.entity.listing.Listing;
import ru.romzheln.listing.model.entity.property.Location;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.model.enums.*;
import ru.romzheln.listing.repository.ListingRepository;
import ru.romzheln.listing.repository.PropertyRepository;
import ru.romzheln.listing.service.OutboxEventService;

@ExtendWith(MockitoExtension.class)
class ListingServiceImplTest {

  @Mock private ListingRepository listingRepository;

  @Mock private OutboxEventService outboxEventService;

  @Mock private ListingMapper listingMapper;

  @Mock private PropertyRepository propertyRepository;

  @Mock private PropertyEventMapper propertyMapper;

  @InjectMocks private ListingServiceImpl listingService;

  private Listing listing;
  private Property property;

  private static final String TITLE = "Title";
  private static final String DESCRIPTION = "Description";
  private static final Long OWNER_ID = 1L;
  private static final Long PROPERTY_ID = 2L;
  private static final Long LISTING_ID = 3L;
  private static final DealType DEAL_TYPE = DealType.SELL;
  private static final BigDecimal PRICE = BigDecimal.valueOf(1000000);
  private static final Region REGION = Region.КРАСНОДАРСКИЙ_КРАЙ;
  private static final CreateListingRequest CREATE_REQUEST =
      new CreateListingRequest(TITLE, DESCRIPTION, OWNER_ID, PROPERTY_ID, DEAL_TYPE, PRICE);
  private static final ListingCreatedEvent CREATED_EVENT = new ListingCreatedEvent(TITLE, DESCRIPTION, OWNER_ID, PROPERTY_ID, DEAL_TYPE, PRICE);
  private static final PropertyEvent PROPERTY_EVENT = ApartmentEvent.builder().build();

  private static final ListingResponse RESPONSE = new ListingResponse(LISTING_ID, TITLE, DESCRIPTION, ListingStatus.CREATED, OWNER_ID, PROPERTY_ID, DEAL_TYPE, PRICE);

  @BeforeEach
  void createData(){
      property = Property.builder()
              .id(PROPERTY_ID)
              .location(new Location(REGION, null, null, null, null, null))
              .propertyType(PropertyType.APARTMENT)
              .build();

      listing = Listing.builder()
              .id(LISTING_ID)
              .title(TITLE)
              .description(DESCRIPTION)
              .status(ListingStatus.CREATED)
              .ownerId(OWNER_ID)
              .property(property)
              .dealType(DEAL_TYPE)
              .price(PRICE)
              .build();

  }

  @Test
  @DisplayName("Создание объявления с существующим объектом недвижимости")
  void shouldCreateListingWhenPropertyExists() {

      when(propertyRepository.findById(PROPERTY_ID)).thenReturn(Optional.of(property));

      when(listingRepository.save(any(Listing.class))).thenReturn(listing);

      when(listingMapper.toListingCreatedEvent(listing)).thenReturn(CREATED_EVENT);

      when(propertyMapper.toPropertyEvent(property)).thenReturn(PROPERTY_EVENT);

      when(listingMapper.toResponse(listing)).thenReturn(RESPONSE);

      var result = listingService.createListing(CREATE_REQUEST);

      assertEquals(RESPONSE, result);

      verify(listingRepository).save(argThat(listing -> listing.getTitle().equals(TITLE) && listing.getDescription().equals(DESCRIPTION) && listing.getOwnerId().equals(OWNER_ID) && listing.getDealType().equals(DEAL_TYPE) && listing.getPrice().equals(PRICE) && listing.getStatus().equals(ListingStatus.CREATED) && listing.getProperty().equals(property)));

      verify(listingMapper).toListingCreatedEvent(listing);

      verify(propertyMapper).toPropertyEvent(property);

      verify(outboxEventService).save(eq(listing.getId()), eq(REGION), eq(EventType.CREATED), eq(PropertyType.APARTMENT), eq(CREATED_EVENT), eq(PROPERTY_EVENT), eq(null));
  }

    @Test
    @DisplayName("При создании объявления не найден объект недвижимости")
    void shouldThrowExceptionWhenPropertyDoesNotExist() {

      when(propertyRepository.findById(PROPERTY_ID)).thenReturn(Optional.empty());

      var exception = assertThrows(PropertyNotFoundByIdException.class, () -> listingService.createListing(CREATE_REQUEST));

      assertEquals("Объект недвижимости с ID 2 не найден", exception.getMessage());

        verify(listingRepository, never()).save(any());

        verify(outboxEventService, never()).save(any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    @DisplayName("Обновление заголовка объявления")
    void shouldUpdateOnlyTitle() {

        UpdateListingRequest request = new UpdateListingRequest("NewTitle", null, null);

        ListingResponse response = new ListingResponse(listing.getId(), request.title(), listing.getDescription(), listing.getStatus(), listing.getOwnerId(), listing.getProperty().getId(), listing.getDealType(), listing.getPrice());

        ListingUpdatedEvent event = new ListingUpdatedEvent(request.title(), listing.getDescription(), listing.getDealType());

      when(listingRepository.findById(LISTING_ID)).thenReturn(Optional.of(listing));

      when(listingMapper.toListingUpdatedEvent(listing)).thenReturn(event);

      when(propertyMapper.toPropertyEvent(listing.getProperty())).thenReturn(PROPERTY_EVENT);

      when(listingMapper.toResponse(listing)).thenReturn(response);

      listingService.updateListing(LISTING_ID, request);

      assertEquals(request.title(), listing.getTitle());
      assertEquals(DESCRIPTION,listing.getDescription());
      assertEquals(DEAL_TYPE, listing.getDealType());

        verify(outboxEventService).save(eq(listing.getId()), eq(REGION), eq(EventType.UPDATED), eq(PropertyType.APARTMENT), eq(event), eq(PROPERTY_EVENT), eq(null));
    }

    @Test
    @DisplayName("Обновление описания объявления")
    void shouldUpdateOnlyDescription() {

        UpdateListingRequest request = new UpdateListingRequest(null, "NewDescription", null);

        ListingResponse response = new ListingResponse(listing.getId(), listing.getTitle(), request.description(), listing.getStatus(), listing.getOwnerId(), listing.getProperty().getId(), listing.getDealType(), listing.getPrice());

        ListingUpdatedEvent event = new ListingUpdatedEvent(listing.getTitle(), request.description(), listing.getDealType());

        when(listingRepository.findById(LISTING_ID)).thenReturn(Optional.of(listing));

        when(listingMapper.toListingUpdatedEvent(listing)).thenReturn(event);

        when(propertyMapper.toPropertyEvent(listing.getProperty())).thenReturn(PROPERTY_EVENT);

        when(listingMapper.toResponse(listing)).thenReturn(response);

        listingService.updateListing(LISTING_ID, request);

        assertEquals(TITLE, listing.getTitle());
        assertEquals(request.description(), listing.getDescription());
        assertEquals(DEAL_TYPE, listing.getDealType());

        verify(outboxEventService).save(eq(listing.getId()), eq(REGION), eq(EventType.UPDATED), eq(PropertyType.APARTMENT), eq(event), eq(PROPERTY_EVENT), eq(null));
    }

    @Test
    @DisplayName("Обновление типа сделки в объявлении")
    void shouldUpdateOnlyDealType() {

        UpdateListingRequest request = new UpdateListingRequest(null, null, DealType.RENT);

        ListingResponse response = new ListingResponse(listing.getId(), listing.getTitle(), listing.getDescription(), listing.getStatus(), listing.getOwnerId(), listing.getProperty().getId(), request.dealType(), listing.getPrice());

        ListingUpdatedEvent event = new ListingUpdatedEvent(listing.getTitle(), listing.getDescription(), request.dealType());

        when(listingRepository.findById(LISTING_ID)).thenReturn(Optional.of(listing));

        when(listingMapper.toListingUpdatedEvent(listing)).thenReturn(event);

        when(propertyMapper.toPropertyEvent(listing.getProperty())).thenReturn(PROPERTY_EVENT);

        when(listingMapper.toResponse(listing)).thenReturn(response);

        listingService.updateListing(LISTING_ID, request);

        assertEquals(TITLE, listing.getTitle());
        assertEquals(DESCRIPTION, listing.getDescription());
        assertEquals(request.dealType(), listing.getDealType());

        verify(outboxEventService).save(eq(listing.getId()), eq(REGION), eq(EventType.UPDATED), eq(PropertyType.APARTMENT), eq(event), eq(PROPERTY_EVENT), eq(null));
    }

    @Test
    @DisplayName("Обновление всех возможных полей в объявлении")
    void shouldAllUpdate() {

        UpdateListingRequest request = new UpdateListingRequest("NewTitle", "NewDescription", DealType.RENT);

        ListingResponse response = new ListingResponse(listing.getId(), request.title(), request.description(), listing.getStatus(), listing.getOwnerId(), listing.getProperty().getId(), request.dealType(), listing.getPrice());

        ListingUpdatedEvent event = new ListingUpdatedEvent(request.title(), request.description(), request.dealType());

        when(listingRepository.findById(LISTING_ID)).thenReturn(Optional.of(listing));

        when(listingMapper.toListingUpdatedEvent(listing)).thenReturn(event);

        when(propertyMapper.toPropertyEvent(listing.getProperty())).thenReturn(PROPERTY_EVENT);

        when(listingMapper.toResponse(listing)).thenReturn(response);

        listingService.updateListing(LISTING_ID, request);

        assertEquals(request.title(), listing.getTitle());
        assertEquals(request.description(), listing.getDescription());
        assertEquals(request.dealType(), listing.getDealType());

        verify(outboxEventService).save(eq(listing.getId()), eq(REGION), eq(EventType.UPDATED), eq(PropertyType.APARTMENT), eq(event), eq(PROPERTY_EVENT), eq(null));
    }

    @Test
    @DisplayName("В запросе обновления все поля null")
    void shouldThrowExceptionWhenAllFieldsAreNull() {

        UpdateListingRequest request = new UpdateListingRequest(null, null, null);

        when(listingRepository.findById(LISTING_ID)).thenReturn(Optional.of(listing));

        var exception = assertThrows(UpdateListingException.class, ()->listingService.updateListing(LISTING_ID, request));

        assertEquals("Нет полей для изменения в объявлении с ID " + LISTING_ID, exception.getMessage());

        assertEquals(TITLE, listing.getTitle());
        assertEquals(DESCRIPTION, listing.getDescription());
        assertEquals(DEAL_TYPE, listing.getDealType());

        verify(listingRepository, never()).save(any());

        verify(outboxEventService, never()).save(any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    @DisplayName("При обновлении объявления, оно не было найдено")
    void shouldThrowExceptionWhenUpdateButListingNotFoundById() {

      when(listingRepository.findById(LISTING_ID)).thenReturn(Optional.empty());

      var exception = assertThrows(ListingNotFoundByIdException.class, ()-> listingService.updateListing(LISTING_ID, new UpdateListingRequest(null, null, null)));

      assertEquals("Объявление с ID " + LISTING_ID + " не найдено", exception.getMessage());

        verify(listingRepository, never()).save(any());

        verify(outboxEventService, never()).save(any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    @DisplayName("При обновлении объекта недвижимости не было найдено ни одного связанного объявления")
    void shouldNotCreateOutboxEventWhenUpdatePropertyButListingNotFound() {

      when(listingRepository.findByPropertyId(PROPERTY_ID)).thenReturn(List.of());

      listingService.updateProperty(EventType.UPDATED_PROPERTY, PROPERTY_ID, null, null);

        verify(outboxEventService, never()).save(any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    @DisplayName("При обновлении объекта недвижимости было найдено одно связанное объявление")
    void shouldCreateOutboxEventWhenUpdateProperty() {

        when(listingRepository.findByPropertyId(PROPERTY_ID)).thenReturn(List.of(listing));

        listingService.updateProperty(EventType.UPDATED_PROPERTY, PROPERTY_ID, null, null);

        verify(outboxEventService, times(1)).save(eq(listing.getId()), eq(listing.getProperty().getLocation().getRegion()), eq(EventType.UPDATED_PROPERTY), eq(listing.getProperty().getPropertyType()), eq(null), eq(null), eq(null));
    }

    @Test
    @DisplayName("При обновлении объекта недвижимости было найдено несколько связанных объявлений")
    void shouldCreateOutboxEventWhenUpdatePropertyAndFindSomeListings() {

      List<Listing> listings = List.of(listing, listing, listing);

      when(listingRepository.findByPropertyId(PROPERTY_ID)).thenReturn(listings);

        listingService.updateProperty(EventType.UPDATED_PROPERTY, PROPERTY_ID, null, null);

        verify(outboxEventService, times(listings.size())).save(any(), any(), any(), any(), any(), any(), any());
    }
}
