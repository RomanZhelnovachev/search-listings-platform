package ru.romzheln.listing.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romzheln.listing.dto.request.property.common.CreatePropertyRequest;
import ru.romzheln.listing.dto.request.property.common.UpdatePropertyRequest;
import ru.romzheln.listing.dto.response.PropertyResponse;
import ru.romzheln.listing.exception.PropertyNotFoundByIdException;
import ru.romzheln.listing.exception.PropertyStrategyNotFoundException;
import ru.romzheln.listing.mapper.PropertyEventMapper;
import ru.romzheln.listing.mapper.PropertyResponseMapper;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.model.enums.AggregateType;
import ru.romzheln.listing.model.enums.EventType;
import ru.romzheln.listing.model.enums.PropertyType;
import ru.romzheln.listing.repository.PropertyRepository;
import ru.romzheln.listing.service.OutboxEventService;
import ru.romzheln.listing.service.PropertyService;
import ru.romzheln.listing.service.strategy.PropertyStrategy;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final OutboxEventService outboxEventService;
    private final Map<PropertyType, PropertyStrategy> strategies;
    private final PropertyResponseMapper responseMapper;
    private final PropertyEventMapper eventMapper;

    @Override
    @Transactional
    public PropertyResponse createProperty(CreatePropertyRequest request) {
        PropertyStrategy strategy = getStrategy(request.getPropertyType());
        Property property = propertyRepository.save(strategy.create(request));
        outboxEventService.save(AggregateType.PROPERTY,
                property.getId(), EventType.CREATED, eventMapper.toPropertyEvent(property));
        log.info("Объект недвижимости с ID {} успешно сохранён", property.getId());
        return responseMapper.toResponse(property);
    }

    @Override
    @Transactional
    public PropertyResponse updateProperty(Long id, UpdatePropertyRequest request) {
        Property property = getProperty(id);
        PropertyStrategy strategy = getStrategy(property.getPropertyType());
        strategy.update(id, request);
        outboxEventService.save(AggregateType.PROPERTY,
                property.getId(), EventType.UPDATED,
                eventMapper.toPropertyEvent(property));
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
        return propertyRepository.findById(id).orElseThrow(()-> new PropertyNotFoundByIdException(id));
    }

    private PropertyStrategy getStrategy(PropertyType type){
        return Optional.ofNullable(strategies.get(type)).orElseThrow(()-> new PropertyStrategyNotFoundException(type));
    }
}
