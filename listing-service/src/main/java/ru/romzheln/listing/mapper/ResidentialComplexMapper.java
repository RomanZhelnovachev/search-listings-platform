package ru.romzheln.listing.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import ru.romzheln.listing.dto.event.ResidentialComplexEvent;
import ru.romzheln.listing.dto.response.ResidentialComplexResponse;
import ru.romzheln.listing.model.entity.common.ResidentialComplex;

import java.util.List;

@Component
public class ResidentialComplexMapper implements ReferenceMapper<ResidentialComplex, ResidentialComplexResponse, ResidentialComplexEvent>{

    public ResidentialComplexResponse toResponse(ResidentialComplex complex){
        return new ResidentialComplexResponse(complex.getName());
    }

    public Page<ResidentialComplexResponse> toPageResponse(Page<ResidentialComplex> complexes){
        List<ResidentialComplexResponse> responses = complexes.getContent()
                .stream()
                .map(this::toResponse)
                .toList();
        return new PageImpl<>(responses, complexes.getPageable(), complexes.getTotalElements());
    }

    public ResidentialComplexEvent toEvent(ResidentialComplex complex){
        return new ResidentialComplexEvent(complex.getName());
    }
}
