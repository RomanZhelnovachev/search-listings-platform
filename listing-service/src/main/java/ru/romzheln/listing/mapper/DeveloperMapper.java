package ru.romzheln.listing.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import ru.romzheln.listing.dto.event.DeveloperEvent;
import ru.romzheln.listing.dto.response.DeveloperResponse;
import ru.romzheln.listing.model.entity.common.Developer;

import java.util.List;

@Component
public class DeveloperMapper implements ReferenceMapper<Developer, DeveloperResponse, DeveloperEvent>{

    public DeveloperResponse toResponse(Developer developer){
        return new DeveloperResponse(developer.getName());
    }

    public Page<DeveloperResponse> toPageResponse(Page<Developer> developers){
        List<DeveloperResponse> responses = developers.getContent()
                .stream()
                .map(this::toResponse)
                .toList();
        return new PageImpl<>(responses, developers.getPageable(), developers.getTotalElements());
    }

    public DeveloperEvent toEvent(Developer developer){
        return new DeveloperEvent(developer.getName());
    }
}
