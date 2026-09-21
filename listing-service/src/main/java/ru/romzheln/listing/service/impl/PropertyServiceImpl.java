package ru.romzheln.listing.service.impl;

import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romzheln.listing.dto.request.property.common.CreatePropertyRequest;
import ru.romzheln.listing.dto.request.property.common.UpdatePropertyRequest;
import ru.romzheln.listing.dto.response.PropertyResponse;
import ru.romzheln.listing.exception.badRequest.InvalidPropertyTypeException;
import ru.romzheln.listing.exception.notFound.PropertyNotFoundByIdException;
import ru.romzheln.listing.exception.notFound.PropertyStrategyNotFoundException;
import ru.romzheln.listing.mapper.PropertyEventMapper;
import ru.romzheln.listing.mapper.PropertyResponseMapper;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.model.enums.EventType;
import ru.romzheln.listing.model.enums.PropertyType;
import ru.romzheln.listing.model.enums.Region;
import ru.romzheln.listing.repository.PropertyRepository;
import ru.romzheln.listing.service.ListingService;
import ru.romzheln.listing.service.PropertyService;
import ru.romzheln.listing.service.strategy.PropertyStrategy;

@Service
@RequiredArgsConstructor
@Slf4j
public class PropertyServiceImpl implements PropertyService {

  private final PropertyRepository propertyRepository;
  private final Map<PropertyType, PropertyStrategy> strategies;
  private final ListingService listingService;
  private final PropertyResponseMapper responseMapper;
  private final PropertyEventMapper eventMapper;

  @Override
  @Transactional
  public PropertyResponse createProperty(CreatePropertyRequest request) {
    PropertyStrategy strategy = getStrategy(request.getPropertyType());
    Property property = propertyRepository.save(strategy.create(request));
    log.info("Объект недвижимости с ID {} успешно сохранён", property.getId());
    return responseMapper.toResponse(property);
  }

  @Override
  @Transactional
  public PropertyResponse updateProperty(Long id, UpdatePropertyRequest request) {
    Property property = getProperty(id);
      Region oldRegion = property.getLocation().getRegion();
    if (request.getPropertyType() != property.getPropertyType()) {
      throw new InvalidPropertyTypeException(id, request.getPropertyType());
    }
    PropertyStrategy strategy = getStrategy(property.getPropertyType());
    strategy.update(id, request);
    listingService.updateProperty(
        EventType.UPDATED_PROPERTY, property.getId(), eventMapper.toPropertyEvent(property), oldRegion);
    log.info("Объект недвижимости с ID {} успешно изменён", property.getId());
    return responseMapper.toResponse(property);
  }

  @Override
  @Transactional(readOnly = true)
  public PropertyResponse findById(Long id) {
    log.info("Получен объект недвижимости с ID {}", id);
    return responseMapper.toResponse(getProperty(id));
  }

  @Override
  @Transactional(readOnly = true)
  public Page<PropertyResponse> getAll(Pageable pageable) {
    log.info("Получен постраничный список объектов недвижимости");
    return responseMapper.toPageResponse(propertyRepository.findAll(pageable));
  }

  @Override
  public Property getProperty(Long id) {
    return propertyRepository.findById(id).orElseThrow(() -> new PropertyNotFoundByIdException(id));
  }
  
    private PropertyStrategy getStrategy(PropertyType type) {
    return Optional.ofNullable(strategies.get(type))
        .orElseThrow(() -> new PropertyStrategyNotFoundException(type));
  }
}
