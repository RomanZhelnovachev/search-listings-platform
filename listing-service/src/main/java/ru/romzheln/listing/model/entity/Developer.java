package ru.romzheln.listing.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

public class Developer {

    @OneToMany(mappedBy = "developer", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Property> properties = new ArrayList<>();
}
