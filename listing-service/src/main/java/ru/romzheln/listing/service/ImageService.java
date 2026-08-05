package ru.romzheln.listing.service;

import ru.romzheln.listing.model.entity.listing.Image;

import java.util.Set;

public interface ImageService {

    Set<Image> findAll(Set<Long> imageIds);
}
