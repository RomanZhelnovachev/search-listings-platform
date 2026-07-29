package ru.romzheln.listing.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.romzheln.listing.exception.OwnerNotFoundException;
import ru.romzheln.listing.model.entity.owner.Owner;
import ru.romzheln.listing.repository.OwnerRepository;
import ru.romzheln.listing.service.OwnerService;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    @Override
    public Owner findOwnerById(Long id) {
        return ownerRepository.findById(id).orElseThrow(()-> new OwnerNotFoundException(id));
    }
}
