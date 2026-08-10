package ru.romzheln.listing.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudService<E, Q, P> {

    P create(Q request);

    P update(Long id, Q request);

    P findById(Long id);

    Page<P> getAll(Pageable pageable);

    E get(Long id);
}
