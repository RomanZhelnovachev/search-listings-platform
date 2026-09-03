package ru.romzheln.listing.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romzheln.listing.dto.request.reference.ResidentialComplexRequest;
import ru.romzheln.listing.dto.response.ResidentialComplexResponse;
import ru.romzheln.listing.exception.notFound.ResidentialComplexNotFoundException;
import ru.romzheln.listing.mapper.ResidentialComplexMapper;
import ru.romzheln.listing.model.entity.common.ResidentialComplex;
import ru.romzheln.listing.repository.ResidentialComplexRepository;
import ru.romzheln.listing.service.CrudService;
import ru.romzheln.listing.service.PropertyService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResidentialComplexServiceImpl
    implements CrudService<
        ResidentialComplex, ResidentialComplexRequest, ResidentialComplexResponse> {

  private final ResidentialComplexRepository repository;
  private final ResidentialComplexMapper mapper;
  private final PropertyService propertyService;

  @Override
  @Transactional
  public ResidentialComplexResponse create(ResidentialComplexRequest request) {
    ResidentialComplex complex = ResidentialComplex.builder().name(request.name()).build();
    ResidentialComplex savedComplex = repository.save(complex);
    log.info("Жилой комплекс с ID {} успешно сохранён", savedComplex.getId());
    return mapper.toResponse(savedComplex);
  }

  @Override
  @Transactional
  public ResidentialComplexResponse update(Long id, ResidentialComplexRequest request) {
    ResidentialComplex complex = getComplex(id);
    complex.setName(request.name());
    propertyService.updateResidentialComplex(complex.getId(), mapper.toEvent(complex));
    log.info("Жилой комплекс с ID {} успешно переименован в {}", id, complex.getName());
    return mapper.toResponse(complex);
  }

  @Override
  @Transactional(readOnly = true)
  public ResidentialComplexResponse findById(Long id) {
    ResidentialComplex complex = getComplex(id);
    log.info("Получен жилой комплекс с ID {}", id);
    return mapper.toResponse(complex);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ResidentialComplexResponse> getAll(Pageable pageable) {
    Page<ResidentialComplex> complexes = repository.findAll(pageable);
    log.info(
        "Получен постраничный список всех жилых комплексов: page = {}, size = {}",
        pageable.getPageNumber(),
        pageable.getPageSize());
    return mapper.toPageResponse(complexes);
  }

  private ResidentialComplex getComplex(Long id) {
    return repository.findById(id).orElseThrow(() -> new ResidentialComplexNotFoundException(id));
  }
}
