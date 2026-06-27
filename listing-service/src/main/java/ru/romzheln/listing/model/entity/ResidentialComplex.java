package ru.romzheln.listing.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

public class ResidentialComplex {

    @OneToMany(mappedBy = "complex", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Property> properties = new ArrayList<>();
}
