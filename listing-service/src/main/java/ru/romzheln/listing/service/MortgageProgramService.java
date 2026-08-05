package ru.romzheln.listing.service;

import ru.romzheln.listing.model.entity.listing.MortgageProgram;

import java.util.Set;

public interface MortgageProgramService {

    Set<MortgageProgram> findAll(Set<Long> mortgageProgramIds);

}
