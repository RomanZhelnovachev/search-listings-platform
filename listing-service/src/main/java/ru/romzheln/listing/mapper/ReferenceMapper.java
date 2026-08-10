package ru.romzheln.listing.mapper;

import org.springframework.data.domain.Page;

public interface ReferenceMapper <E, P, O>{

    P toResponse(E e);

    Page<P> toPageResponse(Page<E> es);

    O toEvent(E e);
}
