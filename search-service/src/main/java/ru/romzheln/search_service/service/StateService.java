package ru.romzheln.search_service.service;

import ru.romzheln.search_service.dto.event.DeveloperEvent;

public interface StateService {

    void saveAndAddDeveloper(Long id, DeveloperEvent event);

    void updateDeveloper(Long id, DeveloperEvent event);
}
