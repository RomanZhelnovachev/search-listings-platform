package ru.romzheln.search_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.romzheln.search_service.dto.event.DeveloperEvent;
import ru.romzheln.search_service.model.enums.AggregateType;
import ru.romzheln.search_service.model.enums.EntityType;
import ru.romzheln.search_service.model.read_model.ReadModel;
import ru.romzheln.search_service.model.state.PendingEntityState;
import ru.romzheln.search_service.repository.StateRepository;
import ru.romzheln.search_service.service.ListingReadModelService;
import ru.romzheln.search_service.service.StateService;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class StateServiceImpl implements StateService {

    private final StateRepository repository;
    private final ListingReadModelService listingReadModelService;

    @Override
    public void saveAndAddDeveloper(Long id,
                                   DeveloperEvent event) {
    }

    @Override
    public void updateDeveloper(Long id,
                                DeveloperEvent event) {

    }

    private void setDeveloperName(Set<ReadModel> models){

    }
}
