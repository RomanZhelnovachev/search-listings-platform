package ru.romzheln.listing.service;

import ru.romzheln.listing.model.entity.owner.Owner;

public interface OwnerService {

    Owner findOwnerById(Long id);
}
